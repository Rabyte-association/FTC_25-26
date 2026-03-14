package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode1 extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {
    double endGameStart;
    boolean isEndGame;
    private Base base;
    private DriveBase drivebase;
    private inTake intake;

    @Override
    public void runOpMode() throws InterruptedException {
        endGameStart = getRuntime() + 90;

        base = new Base(hardwareMap, gamepad1);
        intake = new inTake(base);
        drivebase = new DriveBase(base);
//        Flywheel flywheel = new Flywheel(base);
        int a=0;

        waitForStart();

        while (opModeIsActive()) {
            drivebase.update(gamepad1);
            intake.update(1);
            telemetry.addData("pos", intake.pos);
            telemetry.addData("col", intake.CheckColor());
            telemetry.addData("a", a);
            telemetry.update();

            if(endGameStart >= getRuntime() && !isEndGame) {
                gamepad1.rumbleBlips(3);
                isEndGame = true;
            }

        }
    }
}