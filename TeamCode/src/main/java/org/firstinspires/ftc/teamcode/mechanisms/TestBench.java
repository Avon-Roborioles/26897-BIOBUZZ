package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TestBench {
    private DigitalChannel touchSensor;
    private DcMotor motor;
    private DistanceSensor distanceSensor;
    private Servo servoPos;
    private CRServo servoRot;
    private NormalizedColorSensor colorSensor;
    private double ticksPerRev;

    public void init(HardwareMap hwMap) {
        // TOUCH SENSOR
        touchSensor = hwMap.get(DigitalChannel.class, "touch_sensor");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        // DC MOTOR
        motor = hwMap.get(DcMotor.class, "motor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        ticksPerRev = motor.getMotorType().getTicksPerRev();

        //DISTANCE SENSORS
        distanceSensor = hwMap.get(DistanceSensor.class, "sensor_distance");

        //SERVOS
        servoPos = hwMap.get(Servo.class, "servo_pos");
        servoRot = hwMap.get(CRServo.class, "servo_rot");
        servoPos.scaleRange(0.5,1.0); //set range from mid-point to 180 deg
        servoPos.setDirection(Servo.Direction.REVERSE); // reverse the direction of a servo
        servoRot.setDirection(DcMotorSimple.Direction.REVERSE);

        //COLOR SENSOR
        colorSensor = hwMap.get(NormalizedColorSensor.class, "sensor_color");
    }

    //--------------- TOUCH SENSOR ---------------//
    /*
    This method returns as to whether or not the sensor is pressed
    @ return Returns true if sensor is pressed
     */
    public boolean isTouchSensorPressed() {
        return !touchSensor.getState();
    }
    /*
    This method returns as to whether or not the sensor is released
    @ return Returns true if sensor is released
     */
    public boolean isTouchSensorReleased() {
        return touchSensor.getState();
    }

    //----------------- DC MOTOR ------------------//
    /*
    This method allows the user to set the motor speed
    @ param speed The desired motor speed set by the user
     */
    public void setMotorSpeed(double speed) {
        //accepts values from -1.0 to 1.0
        motor.setPower(speed);
    }
    public double getMotorRevs() {
        //Current number of ticks divided by ticks per revolutions
        return motor.getCurrentPosition()/ticksPerRev;
    }
    public void setBrakeMode(DcMotor.ZeroPowerBehavior zeroBehavior){
        motor.setZeroPowerBehavior(zeroBehavior);
    }

    //-----------------DISTANCE SENSORS ------------------//

    public double getDistance() {
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }

    //---------------- SERVOS -------------------//

   public void setServoRot(double power) {
        servoRot.setPower(power);
   }
   public void setServoPos(double angle) {
        servoPos.setPosition(angle);
   }

   //---------------- COLOR SENSOR ---------------//

}

