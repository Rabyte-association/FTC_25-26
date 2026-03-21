package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {
    double endGameStart;
    boolean isEndGame;
    private DriveBase drivebase;
//    private inTake intake;
    private TuretMechanism turret = new TuretMechanism();
    private Limelight3A limelight;


    @Override
    public void runOpMode() throws InterruptedException {
        endGameStart = getRuntime() + 90;

//        intake = new inTake(hardwareMap);
        drivebase = new DriveBase(hardwareMap);
        turret.init(hardwareMap, telemetry);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
        turret.resetTimer();
        waitForStart();

        while (opModeIsActive()) {
//            telemetry.addData("color: ", intake.CheckColor());
            LLResult result = limelight.getLatestResult();
            //intake.update(0, gamepad1);
            telemetry.update();
            if(endGameStart >= getRuntime() && !isEndGame) {
                gamepad1.rumbleBlips(3);
                isEndGame = true;
            }

            turret.update(result, gamepad1, telemetry);
            drivebase.update(gamepad1);
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
}