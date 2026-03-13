package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
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

            MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
            //Flywheel flywheel = new Flywheel(hardwareMap);
            //inits...

            String currentState = "";

            Vector2d gpp = new Vector2d(-12.0, -40.5);
            Vector2d pgp = new Vector2d(12.0, -40.5);
            Vector2d ppg = new Vector2d(36.0, -40.5);
            Vector2d gate = new Vector2d(0, -65.3);
            Vector2d launchZoneSmall = new Vector2d(48, 0);
            Vector2d launchZoneBig = new Vector2d(0, 0);

            Action takeArtifactsPPG = drive.actionBuilder(drive.localizer.getPose())
                    .strafeToLinearHeading(ppg, Math.toRadians(45))
                    //TODO: take ball
                    .lineToY(-45.5)
                    //TODO: take ball
                    .lineToY(-50.5)
                    //TODO: take ball
                    .build();
            Action takeArtifactsPGP = drive.actionBuilder(drive.localizer.getPose())
                    .strafeToLinearHeading(pgp, Math.toRadians(45))
                    //TODO: take ball
                    .lineToY(-45.5)
                    //TODO: take ball
                    .lineToY(-50.5)
                    //TODO: take ball
                    .build();
            Action takeArtifactsGPP = drive.actionBuilder(drive.localizer.getPose())
                    .strafeToLinearHeading(gpp, Math.toRadians(45))
                    //TODO: take ball
                    .lineToY(-45.5)
                    //TODO: take ball
                    .lineToY(-50.5)
                    //TODO: take ball
                    .build();

            Action goToLaunchZoneSmall = drive.actionBuilder(drive.localizer.getPose())
                    .strafeToLinearHeading(launchZoneSmall, Math.toRadians(45))
                    .build();
            Action goToLaunchZoneBig = drive.actionBuilder(drive.localizer.getPose())
                    .strafeToLinearHeading(launchZoneBig, Math.toRadians(45)) //TODO: zmienic kat
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

