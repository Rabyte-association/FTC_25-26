package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Flywheel {
    DcMotorEx flywheelMotor;
    public Flywheel(Base base) {
        flywheelMotor = base.shootingMotor;
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        PIDFCoefficients pidf = new PIDFCoefficients(0, 0, 0, 0); //change after tuning
        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
    }

    public void shoot() {
        flywheelMotor.setVelocity(1500);
    }
    public void stop() {
        flywheelMotor.setVelocity(0);
    }
    public void update(Gamepad gamepad1) {
        if(gamepad1.cross){
            shoot();
        }
    }

//    public class ShootAuto implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            flywheelMotor.setVelocity(1500);
//            return false;
//        }
//    }
//    Action shootAuto() {
//        return new Flywheel.ShootAuto();
//    }
}