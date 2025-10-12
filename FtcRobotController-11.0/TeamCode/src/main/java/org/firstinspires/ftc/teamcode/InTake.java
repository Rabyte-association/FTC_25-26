import java.util.ArrayList;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import java.util.List;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class InTake{
    private DcMotor MotorIntake;
    private ColorSensor colorSensor;
    private Servo IndexerServo;
    private List<String> balls = new ArrayList<>();
    private int current_index;

    public InTake(HardwareMap hardwareMap) {
        MotorIntake = hardwareMap.get(DcMotor.class, "motorTest");
        IndexerServo = hardwareMap.get(Servo.class, "servo");
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor");
        current_index = 0;

        for (int i=0; i<3; i++) {
            balls.add("Nothing");
        }
        IndexerServo.setPosition(0.0);
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void intake(){
        for (int i=0; i<3; i++){
            SetIndexerServo(i);
            while (CheckColor().equals("Nothing")){
                MotorIntake.setPower(1.0);
                // SetIndexerServo(i);
            }
            MotorIntake.setPower(0.0);
            balls.set(i, CheckColor());
            delay(500);
        }
    }

    public void SetIndexerServo(int index){
        IndexerServo.setPosition((120*index)/360);
        current_index = index;
    }

    public String CheckColor(){
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();
        if (green>100){
            return "Green";
        } else if (blue>green && red>green){
            return "Purple";
        } else{
            return "Nothing";
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
        SetIndexerServo(index);
    }
}
