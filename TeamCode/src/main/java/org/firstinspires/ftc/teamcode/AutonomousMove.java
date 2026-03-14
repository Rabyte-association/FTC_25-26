package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "AutonomousRed", group = "Autonomous")
public class AutonomousMove extends LinearOpMode1 {
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(0, -0, Math.toRadians(90));

        Base base = new Base(hardwareMap, null);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose, base);

        Action move = drive.actionBuilder(initialPose)
                .lineToY(10)
                .waitSeconds(30)
                .build();

        waitForStart();

        while (opModeIsActive()) {
            if (isStopRequested()) return;
            Actions.runBlocking(move);
        }
    }
}
