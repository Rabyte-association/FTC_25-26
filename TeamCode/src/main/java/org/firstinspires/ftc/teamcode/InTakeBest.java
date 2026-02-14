package org.firstinspires.ftc.teamcode;
import java.util.ArrayList;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import java.util.List;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;


public class InTakeBest {
    private final ColorSensor colorSensor;
    private final Servo IndexerServo;
    private final List<String> balls = new ArrayList<>();
    private int current_index;
    private boolean index_is_reversed;
    private int indexer_charge;
    private final int speed_of_intaking = 30; // read the code before changing
    private int intaking_stage;
    private boolean is_intaking;
    private Gamepad gamepad;

    public InTakeBest(HardwareMap hardwareMap, Gamepad gamepad1) {
        IndexerServo = hardwareMap.get(Servo.class, "servo");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        current_index = 0;
        index_is_reversed = false;
        indexer_charge = speed_of_intaking;
        intaking_stage = 0;
        is_intaking = true;
        gamepad = gamepad1;

        for (int i=0; i<3; i++) {
            balls.add("Nothing");
        }
        IndexerServo.setPosition(0.0);
    }

    public void intake(){
        if (index_is_reversed){
            current_index --;
            SetIndexerServo(current_index, false);
        }
        indexer_charge = 0;
        intaking_stage = 1;
        is_intaking = true;
        //start motor
    }
    public void outake(){
        if (!index_is_reversed){
            current_index --;
            SetIndexerServo(current_index, true);
        }
        indexer_charge = 0;
        intaking_stage = 1;
        is_intaking = false;
        //start motor
    }

    public void update(int when_is_green){ // when_is_green represents index of green ball in pattern
        indexer_charge++;
        if (indexer_charge>=speed_of_intaking && intaking_stage!=0 && !CheckColor().equals("Nothing")){
            intaking_stage++;
            if (intaking_stage>3){
                intaking_stage=0;
            }
            indexer_charge=0;
            if (is_intaking) {
                //motor
                SetIndexerServo(intaking_stage - 1, false);
            }else{
                //motor
                if (intaking_stage==when_is_green){
                    SetOutput("Green");
                }else{
                    SetOutput("Purple");
                }
            }
        } else if (gamepad.a){
            intake();
        } else if (gamepad.b) {
            outake();
        }
    }

    public void SetIndexerServo(int index, boolean isOutput){
        if (!isOutput){
            IndexerServo.setPosition((double) (120 * index) /360);
        }else{
            IndexerServo.setPosition((double) (((120 * index)+180) /360)%1.0);
        }
        current_index = index%3;
        index_is_reversed = isOutput;
    }

    public String CheckColor(){
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();
        if (green-3>red && green-3>blue){
            return "Green";
        } else if (blue<5 && green<5 && red<5){
            return "Nothing";
        } else{
            return "Purple";
        }
    }

    public int Red(){
        return colorSensor.red();
    }
    public int Blue(){
        return colorSensor.blue();
    }
    public int Green(){
        return colorSensor.green();
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