package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Subsystems.drivetrain;

@TeleOp(name = "drive test", group = "Test")
public class testdrive extends LinearOpMode {

    public DcMotor frontleft,frontright,backleft,backright;
    public drivetrain drive;

    @Override
    public void runOpMode() {

        frontleft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontright = hardwareMap.get(DcMotor.class,"frontRight");
        backleft = hardwareMap.get(DcMotor.class,"backLeft");
        backright = hardwareMap.get(DcMotor.class,"backRight");

        drive = new drivetrain(frontleft,frontright,backleft,backright);

        waitForStart();

        while (opModeIsActive()) {
            drive.drive(gamepad1.left_stick_y,-gamepad1.left_stick_x,-gamepad1.right_stick_x);
        }
    }
}
