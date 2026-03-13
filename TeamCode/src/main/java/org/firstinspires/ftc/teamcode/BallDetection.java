package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class BallDetection { // contains camera stuff
    static final int STREAM_WIDTH = 320; // modify for your camera
    static final int STREAM_HEIGHT = 240; // modify for your camera
    OpenCvWebcam webcam;
    IntakeCameraPipeline pipeline;
    boolean hasCameraFiled = false;

    public BallDetection(HardwareMap hardwareMap){
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "WebcamIntake");

        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
        pipeline = new IntakeCameraPipeline();
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            public void onOpened() {
                webcam.startStreaming(STREAM_WIDTH, STREAM_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                hasCameraFiled = true;
            }
        });
    }
}
