package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "AutonomousBlue", group = "Autonomous")
public class AutonomousBlue extends LinearOpMode1 {
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(63.0435, -24, Math.toRadians(90));
        if (gamepad1.dpad_right) {
            initialPose = new Pose2d(63.0435, -24, Math.toRadians(90));
        } else if (gamepad1.dpad_left) {
            initialPose = new Pose2d(-63.0435, -24, Math.toRadians(270));
        }

        Base base = new Base(hardwareMap, null);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose, base);
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

        Action goToPPG = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(ppg, Math.toRadians(0))
                .build();
        Action getPPG = drive.actionBuilder(new Pose2d(ppg.x, ppg.y, Math.toRadians(0)))
                .lineToY(-64.5, new TranslationalVelConstraint(30))
                .build();

        Action goToPGP = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(pgp, Math.toRadians(0))
                .build();
        Action getPGP = drive.actionBuilder(new Pose2d(pgp.x, pgp.y, Math.toRadians(0)))
                .lineToY(-64.5, new TranslationalVelConstraint(30))
                .build();

        Action goToGPP = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(gpp, Math.toRadians(0))
                .build();
        Action getGPP = drive.actionBuilder(new Pose2d(gpp.x, gpp.y, Math.toRadians(0)))
                .lineToY(-64.5, new TranslationalVelConstraint(30))
                .build();

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

            intake.outTake();

            currentState = "takeArtifactsPPG";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToPPG);
            intake.inTake();
            while (!intake.IsIndexerFull()){
                intake.update(1);
            }
            Actions.runBlocking(getPPG);

            currentState = "goToLaunchZoneBig";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToLaunchZoneBig);

            intake.outTake();

            currentState = "takeArtifactsPGP";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToPGP);
            intake.inTake();
            while (!intake.IsIndexerFull()){
                intake.update(1);
            }
            Actions.runBlocking(getPGP);

            currentState = "goToLaunchZoneBig";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToLaunchZoneBig);

            intake.outTake();

            currentState = "openGate";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(openGate);

            currentState = "takeArtifactsGPP";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToGPP);
            intake.inTake();
            while (!intake.IsIndexerFull()){
                intake.update(1);
            }
            Actions.runBlocking(getGPP);

            currentState = "goToLaunchZoneBig";
            telemetry.addData("State:", currentState);
            telemetry.update();
            Actions.runBlocking(goToLaunchZoneBig);

            intake.outTake();
        }
    }
}
