package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;


public class inTake {
    private Base base;
    public int pos;
    public ArrayList<Integer> balls;

    inTake(Base base){
        balls = new ArrayList<Integer>();
        this.base=base;
        this.pos=0;
        for(int i=0; i<3; i++) {
            balls.add((Integer) 0);
        }
    }

    private void inTakeBall(){
        int indx=-1;
        for (int i=0; i<3; i++){
            if (balls.get(i)==0){
                indx=i;
            }
        }if (indx==-1){
            return;
        }
        SetIndexerServo(indx, false);
        base.intakeMotor.setPower(0.7);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(timer.seconds() < 2.0) {
        }
        base.intakeMotor.setPower(0.0);
        balls.add(indx, 1);
        SetIndexerServo(indx, true);
    }
    private void OutTakeBall(){
        base.outtakeMotor.setPower(1.0);
        base.shootingMotor.setPower(-1.0);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(timer.seconds() < 1.5) {
        }
        int indx=-1;
        for (int i=0; i<3; i++){
            if (balls.get(i)!=0){
                indx=i;
            }
        }if (indx==-1){
            return;
        }
        SetIndexerServo(indx, true);
        base.intakeMotor.setPower(0.7);
        timer = new ElapsedTime();
        timer.reset();

        while(timer.seconds() < 2.0) {
        }
        base.outtakeServo.setPosition(0.6);
        balls.add(indx, 0);
        timer = new ElapsedTime();
        timer.reset();

        while(timer.seconds() < 2.0) {
        }
        SetIndexerServo(indx, false);
    }

    void update(int grrenIndx){
//        base.intakeMotor.setPower(1.0);
        if (base.gamepad.a){
            this.inTakeBall();
        }else if (base.gamepad.y){
            this.OutTakeBall();
        }else{
            base.outtakeServo.setPosition(0.2);
            base.outtakeMotor.setPower(0.0);
            base.intakeMotor.setPower(0.0);
            base.shootingMotor.setPower(0.0);
        }
    }

    public void SetIndexerServo(int index, boolean isOutput){
        if (index==0 && isOutput){
            base.setIndexerServo(0);
        }if (index==1 && isOutput){
            base.setIndexerServo(138);
        }if (index==2 && isOutput){
            base.setIndexerServo(263);
        }if (index==0 && !isOutput){
            base.setIndexerServo(200);
        }if (index==1 && !isOutput){
            base.setIndexerServo(345);
        }if (index==2 && !isOutput){
            base.setIndexerServo(70);
        }

    }
    public int CheckColor(){
//        int red=2, green=1, blue=0;
        int red = base.Red();
        int green = base.Green();
        int blue = base.Blue();
        if (blue<110 && green<110 && red<110){
            return 0;
        } else if (green*0.8>red && green*0.8>blue){
            return 1;
        } else{
            return 2;
        }
    }
}
