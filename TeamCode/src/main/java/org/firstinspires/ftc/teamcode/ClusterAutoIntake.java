package org.firstinspires.ftc.teamcode;

import com.pedropathing.api.Paths;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.*;
import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.*;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Autonomous(name = "Cluster Auto Intake Please Work", group = "Autonomous")
public class ClusterAutoIntake extends LinearOpMode {

    private Follower follower;
    private Limelight3A limelight;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    public static class BallTarget {
        public double fieldX;
        public double fieldY;
        public String color;
        public double confidence;

        public BallTarget(double fieldX, double fieldY, String color, double confidence) {
            this.fieldX = fieldX;
            this.fieldY = fieldY;
            this.color = color;
            this.confidence = confidence;
        }
    }

    @Override
    public void runOpMode() {
        // Initialize Pedro Pathing v3 Follower using Constants
        follower = Constants.create(hardwareMap);

        // Set your robot's starting field pose (modify X, Y, Heading degrees as needed)
        follower.setPose(poseFactory.of(0, 0, 0));

        // Initialize Limelight 3A Neural Network Pipeline
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // Neural Network Pipeline Index
        limelight.start();

        telemetry.addData("Status", "Initialized Pedro Pathing v3 & Limelight. Waiting for Start.");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            executeClusterIntakeRoutine();
        }
    }

    private void executeClusterIntakeRoutine() {
        int ballsCollected = 0;

        // Loop to acquire up to 4 balls sequentially using cluster pathing
        while (opModeIsActive() && ballsCollected < 4) {
            follower.update(); // Keep odometry and follower updated continuously

            List<BallTarget> pathTargets = computeOptimalClusterPath();

            if (pathTargets.isEmpty()) {
                telemetry.addData("Status", "No targets detected. Scanning...");
                telemetry.update();
                continue;
            }

            // Target the closest ball in the computed cluster sequence
            BallTarget target = pathTargets.get(0);
            telemetry.addData("Target Color", target.color);
            telemetry.addData("Target X", target.fieldX);
            telemetry.addData("Target Y", target.fieldY);
            telemetry.addData("Balls Collected", ballsCollected);
            telemetry.update();

            // Fetch current pose using Pedro v3 syntax (`follower.pose()`)
            Pose currentPose = follower.pose();
            Pose startPose = poseFactory.of(currentPose.x(), currentPose.y(), Math.toDegrees(currentPose.heading()));
            Pose endPose = poseFactory.of(target.fieldX, target.fieldY, Math.toDegrees(currentPose.heading()));

            // Build a dynamic Pedro Path v3 line with linear heading interpolation
            Path intakePath = Paths.line(startPose, endPose).linear(startPose, endPose);

            // Execute path follow command
            follower.follow(intakePath);

            // Wait until the robot reaches the target ball or finishes the path segment
            while (opModeIsActive() && follower.isBusy()) {
                follower.update();

                if (follower.distanceToEndpoint() < 2.0) { // Within 2 inches of intake target
                    break;
                }
            }

            ballsCollected++;
        }

        telemetry.addData("Status", "Cluster intake sequence complete!");
        telemetry.update();
    }

    public List<BallTarget> computeOptimalClusterPath() {
        Pose currentPose = follower.pose();
        LLResult result = limelight.getLatestResult();
        List<BallTarget> detectedBalls = new ArrayList<>();

        if (result != null && result.isValid()) {
            List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();

            for (LLResultTypes.DetectorResult det : detectorResults) {
                String className = det.getClassName().toLowerCase();

                // Filter for red, yellow, or blue balls from the neural network pipeline
                if (className.contains("red") || className.contains("yellow") || className.contains("blue")) {
                    double angleX = Math.toRadians(det.getTargetXDegrees());
                    double distanceY = det.getTargetYDegrees(); // Camera distance model offset

                    double robotHeading = currentPose.heading();

                    double relX = distanceY * Math.cos(angleX);
                    double relY = distanceY * Math.sin(angleX);

                    // Transform camera-relative coordinates into global field coordinates
                    double fieldX = currentPose.x() + (relX * Math.cos(robotHeading) - relY * Math.sin(robotHeading));
                    double fieldY = currentPose.y() + (relX * Math.sin(robotHeading) + relY * Math.cos(robotHeading));

                    detectedBalls.add(new BallTarget(fieldX, fieldY, className, det.getConfidence()));
                }
            }
        }

        // Nearest-Neighbor Cluster Sorting (Chaining up to 4 targets)
        List<BallTarget> sortedPath = new ArrayList<>();
        double currentX = currentPose.x();
        double currentY = currentPose.y();

        while (!detectedBalls.isEmpty() && sortedPath.size() < 4) {
            final double searchX = currentX;
            final double searchY = currentY;

            BallTarget closest = Collections.min(detectedBalls, Comparator.comparingDouble(b ->
                    Math.hypot(b.fieldX - searchX, b.fieldY - searchY)
            ));

            sortedPath.add(closest);
            currentX = closest.fieldX;
            currentY = closest.fieldY;
            detectedBalls.remove(closest);
        }

        return sortedPath;
    }
}