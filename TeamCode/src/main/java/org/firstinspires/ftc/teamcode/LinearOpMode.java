package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Turret Mechanism", group="TeleOp")
public class LinearOpMode extends OpMode {
    private Base base;
    private DriveBase drivebase;
    private InTakeBest intake;

    @Override
    public void init() {
        base = new Base(hardwareMap, gamepad1);
        drivebase = new DriveBase(base);
        intake = new InTakeBest(base);
    }

    @Override
    public void start() {
    }


    @Override
    public void loop() {
        drivebase.update();
        intake.update(0);
        telemetry.update();
    }
}