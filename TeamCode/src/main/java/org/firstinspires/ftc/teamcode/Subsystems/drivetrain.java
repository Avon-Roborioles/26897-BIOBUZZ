package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class drivetrain {
    public final DcMotor frontLeft, frontRight, backLeft, backRight;


    public drivetrain(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.frontRight.setDirection(DcMotor.Direction.REVERSE);
        this.backRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public void drive(double forward, double strafe, double rotate) {
        double frontLeftPower  = forward + strafe + rotate;
        double backLeftPower   = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower  = forward + strafe - rotate;

        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

}
