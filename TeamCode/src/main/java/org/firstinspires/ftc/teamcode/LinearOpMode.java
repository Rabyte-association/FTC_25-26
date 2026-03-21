package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {
    double endGameStart;
    boolean isEndGame;
    private DriveBase drivebase;
    private inTake intake;

    @Override
    public void runOpMode() throws InterruptedException {
        endGameStart = getRuntime() + 90;

        intake = new inTake(hardwareMap);
        drivebase = new DriveBase(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
//            telemetry.addData("color: ", intake.CheckColor());
            intake.update(0, gamepad1);
            telemetry.update();
            if(endGameStart >= getRuntime() && !isEndGame) {
                gamepad1.rumbleBlips(3);
                isEndGame = true;
            }

        }
    }
}