package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {

    private DriveBase drivebase;
    private InTakeBest intake;

    @Override
    public void runOpMode() throws InterruptedException {
        drivebase = new DriveBase(hardwareMap, gamepad1);
        intake = new InTakeBest(hardwareMap, gamepad1);
        waitForStart();

        while (opModeIsActive()) {
            drivebase.update();
            intake.update(0);
            telemetry.addData("ball_detected: ", intake.pipeline.detected);
            telemetry.update();

        }
    }
}