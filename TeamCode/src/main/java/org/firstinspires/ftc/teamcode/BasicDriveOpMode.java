package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;

@TeleOp
public class BasicDriveOpMode extends OpMode {
    ChassisMotors chassis = new ChassisMotors();

    @Override
    public void init() {
        chassis.init(hardwareMap);
    }

    @Override
    public void loop() {
        //get gamepad inputs for power
        double axial = gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.left_stick_x;

        chassis.setFrontRightMotor(axial + lateral + yaw);
        chassis.setFrontLeftMotor(axial - lateral - yaw);
        chassis.setBackRightMotor(axial + lateral - yaw);
        chassis.setBackLeftMotor(axial - lateral + yaw);
    }
}
