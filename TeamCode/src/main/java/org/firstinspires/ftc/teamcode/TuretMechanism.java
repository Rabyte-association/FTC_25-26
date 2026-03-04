package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class TuretMechanism {
    private DcMotor turretMotor;
    private double kP = 0.025;
    private double kD = 0.001;
    private double goalX = 0;
    private double lastError = 0;
    private double angleTolerance = 0.2;
    private final double MAX_POWER = 0.6;
    private double power = 0;
    private double TargetDistance=0;

    private final ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        turretMotor = hwMap.get(DcMotor.class, "turetmotor");
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("Status", "Initialized all systems");
    }
    public void setkP(double newKP) {
        kP = newKP;
    }
    public double getTargetDistance(){return TargetDistance;}
    public double getkP() {
        return kP;
    }
    public void setkD(double newKD) {
        kD = newKD;
    }
    public double getkD() {
        return kD;
    }
    public void resetTimer() {
        timer.reset();
    }

    public double calculateDistance(double area) {
        // To prevent errors, check if area is 0 or negative
        if (area <= 0) {
            return 0.0;
        }

        double distance = 181.156147110456 * Math.pow(area, -0.6267372497901);
        return distance;
    }

    void update(LLResult result, Gamepad gamepad1, Telemetry telemetry){
        double deltaTime = timer.seconds();
        timer.reset();

        if(gamepad1.a){
            kP=kP-0.000001;
        }
        if(gamepad1.x){
            kP=kP+0.000001;
        }
        if(gamepad1.b){
            kD=kD-0.000001;
        }
        if(gamepad1.y){
            kD=kD+0.000001;
        }
        if (result != null && result.isValid()){
            double error = goalX - result.getTx();
            double pTerm = error * kP;

            double dTerm = 0;
            if(deltaTime > 0 ){
                dTerm = ((error - lastError) / deltaTime) * kD;
            }

            if(Math.abs(error) < angleTolerance){
                power = 0;
            }else{
                power = Range.clip(pTerm + dTerm, -MAX_POWER, MAX_POWER);

            }
            // utu garberys pedal jest pedalem i encoder 360 zrop
            turretMotor.setPower(power);
            lastError = error;
        } else{
            turretMotor.setPower(0);
            lastError = 0;
        }
        telemetry.addData("kP Value", kP);
        telemetry.addData("kD Value", kD);

    }
}

