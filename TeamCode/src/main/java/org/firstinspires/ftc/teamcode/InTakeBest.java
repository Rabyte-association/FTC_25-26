package org.firstinspires.ftc.teamcode;
import java.util.ArrayList;

import java.util.List;


public class InTakeBest {
    private final Base base; // every robot component is there
    private final List<String> balls = new ArrayList<>();
    private int current_index; // represents the state of indexer
    private boolean index_is_reversed; // ready for intaking or out taking
    private int indexer_charge; // timer connected to speed_of_intaking
    private final int speed_of_intaking = 30; // cooldown between inputting the balls
    private int outtaking_stage; // one stage for each ball
    private boolean is_intaking; // currently intaking or out taking
    private boolean is_outtaking;
    private Flywheel flywheel;


    public InTakeBest(Base base) {
        this.base = base;
        flywheel = new Flywheel(base);
        current_index = 0;
        index_is_reversed = false;
        indexer_charge = speed_of_intaking;
        outtaking_stage = 0;
        is_intaking = false;
        is_outtaking = false;

        for (int i=0; i<3; i++) {
            balls.add("Nothing");
        }
        this.base.setIndexerServo(0);
    }

    public void inTake(){
        boolean free = isBallInIndexer();
        if (is_outtaking || !free) {
            return;
        }
        if (index_is_reversed){
            current_index --;
            SetIndexerServo(current_index, false);
        }
        indexer_charge = 0;
        is_intaking = true;
        base.intakeMotor.setPower(1.0);
    }
    public void outTake(){
        for (int i=0; i<3; i++){
            if (balls.get(i).equals("Nothing")){
                return;
            }
        }
        if (is_intaking || is_outtaking) {
            return;
        }
        if (!index_is_reversed){
            current_index --;
            SetIndexerServo(current_index, true);
        }
        indexer_charge = 0;
        outtaking_stage = 1;
        is_outtaking = true;
        base.outtakeMotor.setPower(1.0);
    }

    public void reset(){
        is_outtaking=false;
        is_intaking=false;
        outtaking_stage=0;
        base.outtakeMotor.setPower(0.0);
        base.intakeMotor.setPower(0.0);
    }

    public void update(int when_is_green){ // when_is_green represents index of green ball in pattern
        indexer_charge++;
        if (indexer_charge>=speed_of_intaking && is_outtaking){ // ustawiaie serw w dobrych pozycjach pod czas wyrzucania
            base.outtakeServo.setPosition(0.0);// value to tune
            outtaking_stage++;
            indexer_charge=0;
            if (when_is_green-1==outtaking_stage){
                SetOutput("Green");
            } else {
                SetOutput("Purple");
            } if (outtaking_stage>2){
                reset();
            }
        } else if(indexer_charge>=speed_of_intaking-5 && is_outtaking) { // popchnięcie piłki do góry
            base.outtakeServo.setPosition(0.3); // value to tune
        } else if(indexer_charge>=speed_of_intaking-10 && is_outtaking){ // odpalenie silnika
            flywheel.shoot();
        }else if (!CheckColor().equals("Nothing") && is_intaking){ //
            balls.set(current_index, CheckColor());
            reset();
        } else if (base.gamepad.a){
            inTake();
        } else if (base.gamepad.b) {
            outTake();
        }if (indexer_charge>120){ // if intaking takes to much time abort process
            reset();
        }
        flywheel.update(base.gamepad);
    }

    public void updateAutonomous(int when_is_green){ // when_is_green represents index of green ball in pattern
        indexer_charge++;
        if (indexer_charge>=speed_of_intaking && is_outtaking){
            outtaking_stage++;
            indexer_charge=0;
            if (when_is_green-1==outtaking_stage){
                SetOutput("Green");
            } else {
                SetOutput("Purple");
            } if (outtaking_stage>2){
                reset();
            }
        }else if(indexer_charge>=speed_of_intaking-10 && is_outtaking){
            flywheel.shoot();
        } else if (!CheckColor().equals("Nothing") && is_intaking){
            balls.set(current_index, CheckColor());
            reset();
        }if (indexer_charge>120){ // if intaking takes to much time abort process
            reset();
        }if (base.ballDetection.pipeline.detectedPurple && needsPurple()<2 || base.ballDetection.pipeline.detectedGreen && needsGreen()<1) {
            inTake();
        }
        flywheel.update(base.gamepad);
    }

    public void SetIndexerServo(int index, boolean isOutput){
        if (!isOutput){
            base.setIndexerServo(120 * index);
        }else{
            base.setIndexerServo(((120 * index)+180) % 360);
        }
        current_index = (index + 3) % 3;
        index_is_reversed = isOutput;
    }

    public String CheckColor(){
//        int red=2, green=1, blue=0;
        int red = base.Red();
        int green = base.Green();
        int blue = base.Blue();
        if (blue<110 && green<110 && red<110){
            return "Nothing";
        } else if (green*0.8>red && green*0.8>blue){
            return "Green";
        } else{
            return "Purple";
        }
    }


    public void SetOutput(String color){
        int min_distance = 3;
        int index = 0;

        for (int i=0; i<3; i++){
            if (balls.get(i).equals(color) && Math.abs(current_index-i)<min_distance){
                min_distance = Math.abs(current_index-i);
                index = i;
            }
        }
        current_index = index;
        SetIndexerServo(index, true);
        balls.set(index, "Nothing");
    }

    public boolean IsIndexerFull(){
        for (int i=0; i<3; i++){
            if (balls.get(i).equals("Nothing")){
                return false;
            }
        }return true;
    }

    public boolean isBallInIndexer(){
        for (int i=0; i<3; i++){
            if (!balls.get(i).equals("Nothing")){
                return true;
            }
        }return false;
    }

    public int needsPurple(){
        int c=0;
        for (int i=0; i<3; i++){
            if (balls.get(i).equals("Purple")){
                c++;
            }
        }return c;
    }
    public int needsGreen(){
        int c=0;
        for (int i=0; i<3; i++){
            if (balls.get(i).equals("Green")){
                c++;
            }
        }return c;
    }
}