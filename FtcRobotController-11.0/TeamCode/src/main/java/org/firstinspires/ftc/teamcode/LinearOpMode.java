package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {

    private DriveBase drivebase;

    @Override
    public void runOpMode() throws InterruptedException {
        drivebase = new DriveBase(hardwareMap, gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            drivebase.update();
        }
    }
}