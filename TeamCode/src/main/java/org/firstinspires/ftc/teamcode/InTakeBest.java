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
    private int intaking_stage; // one stage for each ball
    private boolean is_intaking; // currently intaking or out taking


    public InTakeBest(Base base) {
        this.base = base;
        current_index = 0;
        index_is_reversed = false;
        indexer_charge = speed_of_intaking;
        intaking_stage = 0;
        is_intaking = true;

        for (int i=0; i<3; i++) {
            balls.add("Nothing");
        }
        this.base.indexerServo.setPosition(0.0);
    }

    public void inTake(){
        if (intaking_stage!=0) {
            return;
        }
        if (index_is_reversed){
            current_index --;
            SetIndexerServo(current_index, false);
        }
        indexer_charge = 0;
        intaking_stage = 1;
        is_intaking = true;
        //start motor
    }
    public void outTake(){
        if (!index_is_reversed){
            current_index --;
            SetIndexerServo(current_index, true);
        }
        indexer_charge = 0;
        intaking_stage = 1;
        is_intaking = false;
        //start motor
    }

    public void reset(){
        intaking_stage= 0;
    }

    public void update(int when_is_green){ // when_is_green represents index of green ball in pattern
        indexer_charge++;
        if (indexer_charge>=speed_of_intaking && intaking_stage!=0 && !CheckColor().equals("Nothing")){
            intaking_stage++;
            intaking_stage%=3;

            indexer_charge=0;
            base.intakeMotor.setPower(0.0);
            base.outtakeMotor.setPower(0.0);
            if (is_intaking) {
                base.intakeMotor.setPower(1.0);
                SetIndexerServo(intaking_stage - 1, false);
            }else {
                base.outtakeMotor.setPower(1.0);
                if (intaking_stage == when_is_green) {
                    SetOutput("Green");
                } else {
                    SetOutput("Purple");
                }
            }
        } else if (base.gamepad.a){
            inTake();
        } else if (base.gamepad.b) {
            outTake();
        }if (indexer_charge>120){ // if intaking takes to much time abort process
            reset();
        }
    }

    public void SetIndexerServo(int index, boolean isOutput){
        if (!isOutput){
            base.indexerServo.setPosition((double) (120 * index) /360);
        }else{
            base.indexerServo.setPosition((double) (((120 * index)+180) /360)%1.0);
        }
        current_index = index%3;
        index_is_reversed = isOutput;
    }

    public String CheckColor(){
        int red = base.Red();
        int green = base.Green();
        int blue = base.Blue();
        if (green-3>red && green-3>blue){
            return "Green";
        } else if (blue<5 && green<5 && red<5){
            return "Nothing";
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
    }
}