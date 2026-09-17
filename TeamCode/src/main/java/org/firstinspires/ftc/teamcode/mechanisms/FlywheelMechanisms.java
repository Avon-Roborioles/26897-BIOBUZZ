package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelMechanisms {
    private DcMotor flywheelLeftMotor, flywheelRightMotor;

    public void init(HardwareMap hwMap) {
        flywheelLeftMotor = hwMap.get(DcMotor.class, "flywheel_left_motor");
        flywheelRightMotor = hwMap.get(DcMotor.class, "flywheel_right_motor");
    }
}
