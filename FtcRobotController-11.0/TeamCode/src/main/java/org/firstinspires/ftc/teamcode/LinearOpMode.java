package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {

    private DriveBase drivebase;
    private Intake intake;

    @Override
    public void runOpMode() throws InterruptedException {
        drivebase = new DriveBase(hardwareMap, gamepad1);
        intake = new InTake(hardwareMap);
        intake.init();

        waitForStart();

        while (opModeIsActive()) {
            drivebase.update();
        }
    }
}