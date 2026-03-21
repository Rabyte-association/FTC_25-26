package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveBase {
    //private Gamepad gamepad1;
    private DcMotor backRight, backLeft, frontRight, frontLeft;
    private double gear = 1;
    private double drive, turn, strafe;
    private double frontLeftPower, frontRightPower, backLeftPower, backRightPower;

    public DriveBase(HardwareMap hardwareMap) {
        //this.gamepad1 = gamepad;

        frontRight = hardwareMap.get(DcMotor.class, "frontRightMotor");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeft = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRight = hardwareMap.get(DcMotor.class, "backRightMotor");

        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void update(Gamepad gamepad1) {
        double controllerSensitivity = 0.05;
        drive  = Math.abs(gamepad1.left_stick_y)  > controllerSensitivity ? -gamepad1.left_stick_y  : 0;
        turn   = Math.abs(gamepad1.right_stick_x) > controllerSensitivity ? -gamepad1.right_stick_x : 0;
        strafe = Math.abs(gamepad1.left_stick_x)  > controllerSensitivity ?  -gamepad1.left_stick_x  : 0;


        frontLeftPower = -(drive + turn + strafe);
        frontRightPower = -(drive - turn - strafe);
        backLeftPower = drive + turn - strafe;
        backRightPower = -(drive - turn + strafe);
//        if (gamepad1.a){ frontLeftPower=1.0;}else{frontLeftPower=0.0;}
//        if (gamepad1.b){ frontRightPower=1.0;}else{frontRightPower=0.0;}
//        if (gamepad1.x){ backLeftPower=1.0;}else{backLeftPower=0.0;}
//        if (gamepad1.y){ backRightPower=1.0;}else{backRightPower=0.0;}

        double[] appliedPowers = scalePowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        frontLeft.setPower(appliedPowers[0] * gear);
        frontRight.setPower(appliedPowers[1] * gear);
        backLeft.setPower(appliedPowers[2] * gear);
        backRight.setPower(appliedPowers[3] * gear);
    }
    public double[] scalePowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if(max >1)
        {
            frontLeftPower /= max;
            frontRightPower /=max;
            backLeftPower /= max;
            backRightPower /= max;
        }
        double [] motorPowers = new double[] {frontLeftPower, frontRightPower, backLeftPower, backRightPower};
        return motorPowers;
    }

}