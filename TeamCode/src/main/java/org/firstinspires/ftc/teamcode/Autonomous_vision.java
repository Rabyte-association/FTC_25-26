package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.List;

@Autonomous (name = "Autonomous_vision_test", group = "Autonomous")
public class Autonomous_vision extends LinearOpMode{
    enum State {
        GET_MOTIF,
        SEARCH_BALL,
        DRIVE_TO_BALL,
        PICK_BALL,
        SHOOT_BALL
    }

    public void runOpMode() throws InterruptedException {
        State currentState = State.GET_MOTIF;
        Pose2d initialPose = new Pose2d(63.5, -24, Math.toRadians(90));

        if(gamepad1.a) {
            initialPose = new Pose2d(63.5, -24, Math.toRadians(90));
        } else if(gamepad1.b) {
            initialPose = new Pose2d(63.5, -24, Math.toRadians(90));
        } else if(gamepad1.y) {
            initialPose = new Pose2d(63.5, -24, Math.toRadians(90));
        } else if(gamepad1.x) {
            initialPose = new Pose2d(63.5, -24, Math.toRadians(90));
        }

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        //inits...
        //Pose2d targetBall = null;

        Action goToBall = drive.actionBuilder(drive.localizer.getPose())
                .strafeTo(new Vector2d(0, 0)) //ball position from vision
                .build();

        waitForStart();

        while(opModeIsActive()) {
            switch (currentState) {
                case GET_MOTIF:;
                    //vision
                    currentState = State.SEARCH_BALL;
                    break;

                case SEARCH_BALL:
//                    List<Pose2d> balls = vision.getBalls();
//
//                    if (!balls.isEmpty()) {
//                        targetBall = getClosestBall(balls, drive.localizer.getPose);
//                        currentState = State.DRIVE_TO_BALL;
//                    }
                    //vision
                    Action search = drive.actionBuilder(drive.localizer.getPose())
                            .turn(Math.toRadians(360))
                            .build();
                    Actions.runBlocking(search);

                    currentState = State.DRIVE_TO_BALL;
                    break;

                case DRIVE_TO_BALL:
                    Actions.runBlocking(goToBall);
                    currentState = State.PICK_BALL;
                    break;

                case PICK_BALL:
                    //intake
                    Action pick = drive.actionBuilder(drive.localizer.getPose())
                            .waitSeconds(3)
                            .build();
                    Actions.runBlocking(pick);

                    currentState = State.SHOOT_BALL;
                    break;

                case SHOOT_BALL:
                    //outtake
                    Action goToBasket = drive.actionBuilder(drive.localizer.getPose())
                            .strafeTo(new Vector2d(24, -63.5))
                            .waitSeconds(3)
                            .build();
                    Actions.runBlocking(goToBasket);

                    currentState = State.SEARCH_BALL;
                    break;
            }
            telemetry.addData("State", currentState);
            telemetry.update();
        }
    }
}
