package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanisms.TestBench;

@TeleOp
public class DcMotorPractice extends OpMode {
    TestBench bench = new TestBench();

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        double motorSpeed = gamepad1.left_stick_y;

        if (gamepad1.a) {
            bench.setBrakeMode(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else if (gamepad1.b) {
            bench.setBrakeMode(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        bench.setMotorSpeed(motorSpeed);
        telemetry.addData("Motor Revs", bench.getMotorRevs());
    }
}
