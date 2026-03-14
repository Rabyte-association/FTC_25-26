package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "AutonomousMoveForward", group = "Autonomous")
public class AutonomousMove extends LinearOpMode {

    Base base;

    @Override
    public void runOpMode() throws InterruptedException {

        base = new Base(hardwareMap, gamepad1);

        waitForStart();

        if (opModeIsActive()) {
            base.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            base.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            base.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            base.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            base.frontLeftMotor.setPower(-0.5);
            base.frontRightMotor.setPower(0.5);
            base.backLeftMotor.setPower(0.5);
            base.backRightMotor.setPower(0.5);

            sleep(2000); // 2s

            base.frontLeftMotor.setPower(0);
            base.frontRightMotor.setPower(0);
            base.backLeftMotor.setPower(0);
            base.backRightMotor.setPower(0);
        }
    }
}