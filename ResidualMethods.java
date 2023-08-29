package cnn;

import java.util.Random;

public class ResidualMethods {
    public static void setResWeight(double[][][] core) {
        for (int c = 0; c < core.length; c++) {
            for (int j = 0; j < core[0].length; j++) {
                for (int k = 0; k < core[0][0].length; k++) {
                    core[c][j][k] = 0.01 * new Random().nextGaussian();
                }
            }
        }
    }

    public static void setCanImage(double[][][] canImage, double[][][] inputImage, int channel) {
        for (int i = 0; i < inputImage.length; i++) {
            for (int j = 0; j < inputImage[0].length; j++) {
                for (int k = 0; k < inputImage[0][0].length; k++) {
                    canImage[i][j][k] = (inputImage[i][j][k]) / channel;
                }
            }
        }
    }

    public static void setOutImage(double[][][] outImage, double[][][] canImage, int stride) {
        PoolMethods.setPoolResult(outImage, canImage, 2, stride);
    }
}
