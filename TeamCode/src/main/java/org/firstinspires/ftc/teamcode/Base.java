package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Base { // class contains every electronic part of the robot
    DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, intakeMotor, outtakeMotor;
    ColorSensor intakeSensor;
    Servo indexerServo;
    Gamepad gamepad;
    BallDetection ballDetection;

    public Base(HardwareMap hardwareMap, Gamepad pad){
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        outtakeMotor = hardwareMap.get(DcMotor.class, "outtakeMotor");

        intakeSensor = hardwareMap.get(ColorSensor.class, "intakeSensor");

        indexerServo = hardwareMap.get(Servo.class, "indexerServo");

        gamepad = pad;

        ballDetection = new BallDetection(hardwareMap);
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
}
