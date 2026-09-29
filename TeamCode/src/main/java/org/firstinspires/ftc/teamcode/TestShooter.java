package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Motor Servo Test", group = "Test")
public class TestShooter extends LinearOpMode {

    private DcMotor motor1;
    private DcMotor motor2;
    private Servo servo1;

    @Override
    public void runOpMode() {

        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        servo1 = hardwareMap.get(Servo.class, "servo1");

        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.FORWARD);

        double motorPower = 0;
        double servoPosition = 0.5;

        servo1.setPosition(servoPosition);

        waitForStart();

        while (opModeIsActive()) {

            // Both motors run at the same speed
            motor1.setPower(motorPower);
            motor2.setPower(motorPower);

            // Left bumper: increase motor speed
            if (gamepad1.left_bumper) {
                motorPower -= 0.05;
            }

            // Right bumper: decrease motor speed
            if (gamepad1.right_bumper) {
                motorPower += 0.05;
            }

            // Y: increase servo position
            if (gamepad1.y) {
                servoPosition += 0.05;
            }

            // A: decrease servo position
            if (gamepad1.a) {
                servoPosition -= 0.05;
            }

            servo1.setPosition(servoPosition);

            telemetry.addData("Motor Power", "%.2f", motorPower);
            telemetry.addData("Servo Position", "%.2f", servoPosition);
            telemetry.update();

            sleep(20);
        }
    }
}
