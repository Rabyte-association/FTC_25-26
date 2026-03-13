package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "AutonomousBlue", group = "Autonomous")
public class AutonomousBlue extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(63.0435, -24, Math.toRadians(90));
        if (gamepad1.dpad_right) {
            initialPose = new Pose2d(63.0435, -24, Math.toRadians(90));
        } else if (gamepad1.dpad_left) {
            initialPose = new Pose2d(-63.0435, -24, Math.toRadians(270));
        }

        Base base = new Base(hardwareMap, null);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        InTakeBest intake = new InTakeBest(base);
        //Flywheel flywheel = new Flywheel(hardwareMap);
        //inits...

        String currentState = "";

        Vector2d gpp = new Vector2d(-12.0, -40.5);
        Vector2d pgp = new Vector2d(12.0, -40.5);
        Vector2d ppg = new Vector2d(36.0, -40.5);
        Vector2d gate = new Vector2d(0, -65.3);
        Vector2d launchZoneSmall = new Vector2d(48, 0);
        Vector2d launchZoneBig = new Vector2d(0, 0);

        Action movePPG1 = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(ppg, Math.toRadians(45))
                .build();
        Action movePPG2 = drive.actionBuilder(new Pose2d(ppg.x, ppg.y, Math.toRadians(0)))
                .lineToY(ppg.y-5.0)
                .build();
        Action movePPG3 = drive.actionBuilder(new Pose2d(ppg.x, ppg.y-5.0, Math.toRadians(0)))
                .lineToY(ppg.y-10.0)
                .build();

        Action movePGP1 = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(pgp, Math.toRadians(45))
                .build();
        Action movePGP2 = drive.actionBuilder(new Pose2d(pgp.x, pgp.y, Math.toRadians(0)))
                .lineToY(pgp.y-5.0)
                .build();
        Action movePGP3 = drive.actionBuilder(new Pose2d(pgp.x, pgp.y-5.0, Math.toRadians(0)))
                .lineToY(pgp.y-10.0)
                .build();

        Action moveGPP1 = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(gpp, Math.toRadians(45))
                .build();
        Action moveGPP2 = drive.actionBuilder(new Pose2d(gpp.x, gpp.y, Math.toRadians(0)))
                .lineToY(gpp.y-5.0)
                .build();
        Action moveGPP3 = drive.actionBuilder(new Pose2d(gpp.x, gpp.y-5.0, Math.toRadians(0)))
                .lineToY(gpp.y-10.0)
                .build();



        Action takeArtifactsPPG = new SequentialAction(
                movePPG1,
                movePPG2,
                movePPG3
        );
        Action takeArtifactsPGP = new SequentialAction(
                movePGP1,
                //TODO: take ball
                movePGP2,
                //TODO: take ball
                movePGP3
                //TODO: take ball
        );
        Action takeArtifactsGPP = new SequentialAction(
                moveGPP1,
                //TODO: take ball
                moveGPP2,
                //TODO: take ball
                moveGPP3
                //TODO: take ball
        );

//            Action takeArtifactsPPG = drive.actionBuilder(drive.localizer.getPose())
//                    .strafeToLinearHeading(ppg, Math.toRadians(45))
//                    //TODO: take ball
//                    .lineToY(-45.5)
//                    //TODO: take ball
//                    .lineToY(-50.5)
//                    //TODO: take ball
//                    .build();
//            Action takeArtifactsPGP = drive.actionBuilder(drive.localizer.getPose())
//                    .strafeToLinearHeading(pgp, Math.toRadians(45))
//                    //TODO: take ball
//                    .lineToY(-45.5)
//                    //TODO: take ball
//                    .lineToY(-50.5)
//                    //TODO: take ball
//                    .build();
//            Action takeArtifactsGPP = drive.actionBuilder(drive.localizer.getPose())
//                    .strafeToLinearHeading(gpp, Math.toRadians(45))
//                    //TODO: take ball
//                    .lineToY(-45.5)
//                    //TODO: take ball
//                    .lineToY(-50.5)
//                    //TODO: take ball
//                    .build();

        Action goToLaunchZoneSmall = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(launchZoneSmall, Math.toRadians(45)) //TODO: zmienic kat
                .build();
        Action goToLaunchZoneBig = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(launchZoneBig, Math.toRadians(45))
                .build();

        Action openGate = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(gate, Math.toRadians(0))
                .build();



        waitForStart();

        while (opModeIsActive()) {
            if (isStopRequested()) return;
            //TODO: get motif

            //TODO: wystrzelic pilki

            currentState = "takeArtifactsPPG";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(takeArtifactsPPG);

            currentState = "goToLaunchZoneBig";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToLaunchZoneBig);

            //TODO: wystrzelic pilki

            currentState = "takeArtifactsPGP";
            telemetry.addData("State:", currentState);
            telemetry.update();
            intake.inTake();
            intake.update(0);
            Actions.runBlocking(takeArtifactsPGP);

            currentState = "goToLaunchZoneBig";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToLaunchZoneBig);

            //TODO: wystrzelic pilki

            currentState = "openGate";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(openGate);

            currentState = "takeArtifactsGPP";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(takeArtifactsGPP);

            currentState = "goToLaunchZoneBig";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToLaunchZoneBig);

            //TODO: wystrzelic pilki
        }
    }
}

