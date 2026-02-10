package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LinearOpMode extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {

//    private DriveBase drivebase;
    private InTakeBest intake;

    @Override
    public void runOpMode() throws InterruptedException {
//        drivebase = new DriveBase(hardwareMap, gamepad1);
        intake = new InTakeBest(hardwareMap, gamepad1);
        String color;

        waitForStart();

        while (opModeIsActive()) {
//            drivebase.update();
            color = intake.CheckColor();
            telemetry.addData("color", color);
            telemetry.update();

        }
    }
}