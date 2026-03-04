package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Turret Mechanism", group="TeleOp")
public class TurretMachanismOpMode extends OpMode {

    private TuretMechanism turret = new TuretMechanism();
    private Limelight3A limelight;
    @Override
    public void init() {
        turret.init(hardwareMap, telemetry);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void start() {
        turret.resetTimer();
    }


    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        turret.update(result, gamepad1, telemetry);

        if(result != null && result.isValid()){
            telemetry.addData("Tx", result.getTx());
            telemetry.addData("Ta", result.getTa());
            telemetry.addData("Target Distance", turret.calculateDistance(result.getTa()));
            telemetry.update();
        }
        else{
            telemetry.addData("Tx", "No data");
            telemetry.update();
        }
    }
}
