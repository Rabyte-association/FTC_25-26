package org.firstinspires.ftc.teamcode;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Scalar;
import java.util.ArrayList;
public class IntakeCameraPipeline extends OpenCvPipeline {

    Mat hsv = new Mat();
    boolean detected = false;


    /*
     * This function takes the RGB frame, converts to YCrCb,
     * and extracts the Y channel to the 'Y' variable
     */
    void inputToHsv(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
        ArrayList<Mat> hsvChannels = new ArrayList<Mat>(3);

    }

    @Override
    public void init(Mat firstFrame) {
        inputToHsv(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToHsv(input);
        Scalar lower = new Scalar(155, 100, 100);
        Scalar upper = new Scalar(175, 255, 255);

        Mat mask = new Mat();
        Core.inRange(hsv, lower, upper, mask);

        double area = Core.sumElems(mask).val[0];
        detected = area > 50000;

        return mask;
    }
}