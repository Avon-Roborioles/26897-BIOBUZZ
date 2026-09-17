package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ChassisMotors {
    private DcMotor frontRightMotor,frontLeftMotor,backRightMotor, backLeftMotor;
    public void init(HardwareMap hwMap) {
        frontRightMotor = hwMap.get(DcMotor.class, "front_right_motor");
        frontLeftMotor = hwMap.get(DcMotor.class, "front_left_motor");
        backRightMotor = hwMap.get(DcMotor.class, "back_right_motor");
        backLeftMotor = hwMap.get(DcMotor.class, "back_left_motor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /*
    //-------------------SETTING UP DIRECTIONS----------------//
    public void setFrontRightMotorDirection(DcMotorSimple.Direction direction) {
        frontRightMotor.setDirection(direction);
    }
    public void setFrontLeftMotorDirection(DcMotorSimple.Direction direction) {
        frontLeftMotor.setDirection(direction);
    }
    public void setBackRightMotorDirection(DcMotorSimple.Direction direction) {
        backRightMotor.setDirection(direction);
    }
    public void setBackLeftMotorDirection(DcMotorSimple.Direction direction) {
        backLeftMotor.setDirection(direction);
    }
     */

    //-------------------SETTING UP POWER-----------------//
    public void setFrontRightMotor(double frontRightMotorPower) {
        frontRightMotor.setPower(frontRightMotorPower);
    }
    public void setFrontLeftMotor(double frontLeftMotorPower) {
        frontLeftMotor.setPower(frontLeftMotorPower);
    }
    public void setBackRightMotor(double backRightMotorPower) {
        backRightMotor.setPower(backRightMotorPower);
    }
    public void setBackLeftMotor(double backLeftMotorPower) {
        backLeftMotor.setPower(backLeftMotorPower);
    }

}
