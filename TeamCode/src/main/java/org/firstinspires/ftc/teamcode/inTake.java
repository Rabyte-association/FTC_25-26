package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class inTake {
    public ColorSensor colorSensor;
    public DcMotor outTakeMotor;
    public Servo indexerServo1, indexerServo2;
    public DigitalChannel distSensor;
    public int pos;
    public ArrayList<Integer> balls;
    public boolean isInTaking;
    public boolean isOutTaking;
    public double startedInTaking;
    public double startedOutTaking;
    public ElapsedTime timer;
    public int detectedBall;
    public static final ArrayList<Integer> positions;

    static {
        positions = new ArrayList<>(); //TODO: Tune
        positions.add(0);
        positions.add(120);
        positions.add(240);
        positions.add(180);
        positions.add(300);
        positions.add(60);
    }

    inTake(HardwareMap hardwareMap){
        balls = new ArrayList<Integer>();
        timer = new ElapsedTime();

        pos = 0;
        detectedBall=0;
        isInTaking = false;
        isOutTaking = false;

        indexerServo1 = hardwareMap.get(Servo.class, "indexerServo1");
//        indexerServo2 = hardwareMap.get(Servo.class, "indexerServo2");
        outTakeMotor = hardwareMap.get(DcMotor.class, "outTakeMotor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        distSensor = hardwareMap.get(DigitalChannel.class, "distSensor");
        distSensor.setMode(DigitalChannel.Mode.INPUT);

        for(int i=0; i<3; i++) {
            balls.add(0);
        }
    }

    private void inTakeBall(){
        if (getIndex(0)==-1 || isInTaking || isOutTaking) return;
        SetIndexerServo(getIndex(0), false);
        pos = getIndex(0);
        startedInTaking = timer.time(TimeUnit.SECONDS);
        isInTaking=true;
    }
    private void OutTakeBall(int color){
        if (getIndex(color)==-1 || isInTaking || isOutTaking) return;
        outTakeMotor.setPower(1.0);
        SetIndexerServo(getIndex(color), true);
        pos = getIndex(color);
        startedOutTaking = timer.time(TimeUnit.SECONDS);
        isOutTaking=true;
    }

    public void update(int grrenIndx, Gamepad gamepad){
        CheckSensor();
        if (isInTaking){
            if (CheckColor()!=0){ // ball found
                balls.add(pos, CheckColor());
                isInTaking=false;
            } else if (timer.time(TimeUnit.SECONDS)-startedInTaking==(float) 2) { //time out
                isInTaking = false;
            }
//        }else if (isOutTaking){
//            if (detectedBall>10){
//                balls.add(pos, 0);
//                isOutTaking=false;
//            } else if (timer.time(TimeUnit.SECONDS)-startedOutTaking==(float) 2) { //time out
//                isOutTaking = false;
//            }
        }
    }

    private void SetIndexerServo(int index, boolean isOutput){
        index = index + (isOutput ? 3 : 0);
        indexerServo1.setPosition(positions.get(index));
//        indexerServo2.setPosition(positions.get(index));
    }

    private void CheckSensor(){
        if (!distSensor.getState()){
            detectedBall++;
        } else{
            detectedBall=0;
        }
    }

    private int howManyInIndexer(int color){
        int counter = 0;
        for (int i=0; i<3; i++){
            if (balls.get(i)==color){
                counter++;
            }
        }
        return counter;
    }

    private int getIndex(int color){
        for (int i=0; i<3; i++){
            if (balls.get(i)==color){
                return i;
            }
        }
        return -1;
    }

    public int CheckColor(){
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();
        if (blue<110 && green<110 && red<110){
            return 0;
        } else if (green*0.8>red && green*0.8>blue){
            return 1;
        } else{
            return 2;
        }
    }
}
