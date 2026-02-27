package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import java.util.ArrayList;

public class IntakeCameraPipeline extends OpenCvPipeline {

    Mat hsv = new Mat();
    Mat maskGreen = new Mat();
    Mat maskPurple = new Mat();
    public boolean detectedGreen = false;
    public boolean detectedPurple = false;

    // Zamiana RGB -> HSV
    void inputToHsv(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
    }

    @Override
    public void init(Mat firstFrame) {
        inputToHsv(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToHsv(input);

        // Zakres fioletu (Hue 155-175)
        Scalar lowerPurple = new Scalar(145, 100, 100);
        Scalar upperPurple = new Scalar(220, 255, 255);

        // Zakres zielonego (Hue ~50-80)
        Scalar lowerGreen = new Scalar(50, 100, 100);
        Scalar upperGreen = new Scalar(80, 255, 255);

        // Maski
        Core.inRange(hsv, lowerPurple, upperPurple, maskPurple);
        Core.inRange(hsv, lowerGreen, upperGreen, maskGreen);

        // Sprawdzenie wykrycia
        double areaPurple = Core.sumElems(maskPurple).val[0];
        double areaGreen = Core.sumElems(maskGreen).val[0];
        detectedPurple = areaPurple > 5000; // dopasuj threshold do kamery
        detectedGreen = areaGreen > 5000;

        // Tworzymy wynikowy Mat do podglądu w DS
        Mat output = new Mat(input.rows(), input.cols(), CvType.CV_8UC3);
        output.setTo(new Scalar(0, 0, 0)); // czarne tło

        // Nakładanie fioletowych pikseli
        for (int y = 0; y < maskPurple.rows(); y++) {
            for (int x = 0; x < maskPurple.cols(); x++) {
                if (maskPurple.get(y, x)[0] > 0) {
                    output.put(y, x, new double[]{255, 0, 255}); // fioletowy
                }
            }
        }

        // Nakładanie zielonych pikseli
        for (int y = 0; y < maskGreen.rows(); y++) {
            for (int x = 0; x < maskGreen.cols(); x++) {
                if (maskGreen.get(y, x)[0] > 0) {
                    output.put(y, x, new double[]{0, 255, 0}); // zielony
                }
            }
        }

        return output;
    }
}