package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Base { // class contains every electronic part of the robot
    DcMotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, intakeMotor, outtakeMotor, turretMotor, shootingMotor;
    ColorSensor intakeSensor;
    Servo indexerServo1, indexerServo2, outtakeServo, turretServo;
    Gamepad gamepad;
    BallDetection ballDetection;

    DistanceSensor distanceSensor;

    public Base(HardwareMap hardwareMap, Gamepad pad){
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "mot4");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "mot3");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "mot2");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "mot1");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        outtakeServo = hardwareMap.get(Servo.class, "outtakeServo");

        intakeSensor = hardwareMap.get(ColorSensor.class, "intakeSensor");

        indexerServo1 = hardwareMap.get(Servo.class, "indexerServo1");
        indexerServo2 = hardwareMap.get(Servo.class, "indexerServo2");

        shootingMotor = hardwareMap.get(DcMotorEx.class, "shootingMotor");
        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");

        turretServo = hardwareMap.get(Servo.class, "turretServo");

        distanceSensor = hardwareMap.get(DistanceSensor.class, "distSensor");

        gamepad = pad;

        ballDetection = new BallDetection(hardwareMap);
        intakeSensor.enableLed(true);
    }
    public int Red(){
        return intakeSensor.red();
    }
    public int Blue(){
        return intakeSensor.blue();
    }
    public int Green(){
        return intakeSensor.green();
    }

    public void  setIndexerServo(int degrees){
        indexerServo1.setPosition(degrees/(360*5));
        indexerServo2.setPosition(degrees/(360*5));
    }
    public double dist(){
        return distanceSensor.getDistance(DistanceUnit.CM);
    }
}
