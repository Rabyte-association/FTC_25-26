package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode1 extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {
    double endGameStart;
    boolean isEndGame;
    private Base base;
    private DriveBase drivebase;
    private InTakeBest intake;

    @Override
    public void runOpMode() throws InterruptedException {
        endGameStart = getRuntime() + 90;

        base = new Base(hardwareMap, gamepad1);
        intake = new InTakeBest(base);
        drivebase = new DriveBase(base);
        Flywheel flywheel = new Flywheel(base);

        waitForStart();

        while (opModeIsActive()) {
            drivebase.update(gamepad1);
            intake.update(1);
            flywheel.update(gamepad1);

            if(endGameStart >= getRuntime() && !isEndGame) {
                gamepad1.rumbleBlips(3);
                isEndGame = true;
            }

        }
    }
}