package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {
    private Base base;
    private DriveBase drivebase;
    private InTakeBest intake;

    @Override
    public void runOpMode() throws InterruptedException {
        base = new Base(hardwareMap, gamepad1);
        drivebase = new DriveBase(base);
        intake = new InTakeBest(base);
        waitForStart();

        while (opModeIsActive()) {
            drivebase.update();
            intake.update(0);
            telemetry.addData("ball_detected: ", base.ballDetection.pipeline.detectedGreen || base.ballDetection.pipeline.detectedPurple);
            telemetry.update();

        }
    }
}