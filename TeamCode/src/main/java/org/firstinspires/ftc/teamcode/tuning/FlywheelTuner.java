package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp (name = "FlywheelTuner", group = "Tuning")
public class FlywheelTuner extends OpMode {
    public DcMotorEx flywheelMotor;
    public double highVel = 1500;
    public double lowVel = 900;
    public double curTargetVel = highVel;
    public double F = 0;
    public double P = 0;
    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 1;


    @Override
    public void init() {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "motor");
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
    }

    @Override
    public void loop() {
        if(gamepad1.triangleWasPressed()) {
            if(curTargetVel == highVel) {
                curTargetVel = lowVel;
            } else {
                curTargetVel = highVel;
            }
        }

        if(gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if(gamepad1.dpadRightWasPressed()) {
            F += stepSizes[stepIndex];
        }
        if(gamepad1.dpadLeftWasPressed()) {
            F -= stepSizes[stepIndex];
        }

        if(gamepad1.dpadUpWasPressed()) {
            P += stepSizes[stepIndex];
        }
        if(gamepad1.dpadDownWasPressed()) {
            P -= stepSizes[stepIndex];
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        flywheelMotor.setVelocity(curTargetVel);

        double curVel = flywheelMotor.getVelocity();
        double error = curTargetVel - curVel;

        telemetry.addData("Target Velocity", curTargetVel);
        telemetry.addData("Current Velocity", "%.2f", curVel);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("------------------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);
    }
}
