package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {
    double endGameStart;
    boolean isEndGame;
    private InTakeBest intake;

    @Override
    public void runOpMode() throws InterruptedException {
        endGameStart = getRuntime() + 90;

        DriveBase drivebase = new DriveBase(hardwareMap);
        //intake = new InTakeBest(hardwareMap, gamepad1);
        //String color;
        Flywheel flywheel = new Flywheel(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            drivebase.update(gamepad1);
            //color = intake.CheckColor();
            //telemetry.addData("color", color);
            //telemetry.update();

            if(endGameStart >= getRuntime() && !isEndGame) {
                gamepad1.rumbleBlips(3);
                isEndGame = true;
            }

        }
    }
}