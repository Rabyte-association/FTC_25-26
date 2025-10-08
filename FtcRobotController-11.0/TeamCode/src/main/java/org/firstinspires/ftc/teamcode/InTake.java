import java.util.ArrayList;
import com.qualcomm.robotcore.hardware.ColorSensor;
import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp

public class InTake{
    private DcMotor MotorIntake;
    private ColorSensor ColorSensor;
    private Servo IndexerServo;
    private List<String> balls = new ArrayList<>();
    private int current_index;

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
        int red = ColorSensor.red();
        int green = ColorSensor.green();
        int blue = ColorSensor.blue();
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


    public void init(LinearOpMode opMode) {
        MotorIntake = opMode.hardwareMap.get(DcMotor.class, "motorTest");
        IndexerServo = opMode.hardwareMap.get(Servo.class, "servo");
        ColorSensor = opMode.hardwareMap.get(ColorSensor.class, "sensor");
        current_index = 0;

        for (int i=0; i<3; i++) {
            balls.add("Nothing");
        }
        IndexerServo.setPosition(0.0);
    }
}
