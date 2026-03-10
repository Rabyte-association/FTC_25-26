package org.firstinspires.ftc.teamcode;

public class DriveBase { // mapping gamepad input to the power of motors
    private final Base base;
    final double gear = 1;
    double drive, turn, strafe;
    double frontLeftPower, frontRightPower, backLeftPower, backRightPower;
    double [] motorPowers;

    public DriveBase(Base base) {
        this.base = base;
    }
    public void update() {
        double controllerSensitivity = 0.05;
        drive  = Math.abs(base.gamepad.left_stick_x)  > controllerSensitivity ? -base.gamepad.left_stick_x  : 0;
        turn   = Math.abs(base.gamepad.left_stick_y) > controllerSensitivity ? -base.gamepad.left_stick_y : 0;
        strafe = Math.abs(base.gamepad.right_stick_x)  > controllerSensitivity ?  base.gamepad.right_stick_x  : 0;

        frontLeftPower = drive + turn - strafe;
        frontRightPower = drive - turn - strafe;
        backLeftPower = drive + turn + strafe;
        backRightPower = drive - turn + strafe;

        double[] appliedPowers = scalePowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        base.frontLeftMotor.setPower(appliedPowers[0] * gear);
        base.frontRightMotor.setPower(appliedPowers[1] * gear);
        base.backLeftMotor.setPower(appliedPowers[2] * gear);
        base.backRightMotor.setPower(appliedPowers[3] * gear);
    }
    public double[] scalePowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if(max >1)
        {
            frontLeftPower /= max;
            frontRightPower /=max;
            backLeftPower /= max;
            backRightPower /= max;
        }
        motorPowers = new double[]{frontLeftPower, frontRightPower, backLeftPower, backRightPower};
        return motorPowers;
    }

}