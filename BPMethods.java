package cnn;

import java.util.Random;

public class BPMethods {
    public static void setBPWeight(double[][] w) {
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[0].length; j++) {
                w[i][j] = 0.01 * new Random().nextGaussian();
            }
        }
    }

    public static void setInputLayer(double[] input, double[][][] pic) {
        int index = 0;
        for (int i = 0; i < pic.length; i++) {
            for (int j = 0; j < pic[0].length; j++) {
                for (int k = 0; k < pic[0][0].length; k++) {
                    input[index] = pic[i][j][k];
                    index++;
                }
            }
        }
    }

    public static void setHiddenLayer(double[] hidden, double[] input, double[][] w) {
        for (int j = 0; j < hidden.length; j++) {
            double sum = 0;
            for (int i = 0; i < w.length; i++) {
                sum += input[i] * w[i][j];
            }
            hidden[j] = Tool.sigmoid(sum); //激活后输出
        }
    }

    public static void setOutLayerT(double[] t, double[] sigmoidOut, double[] E) {
        for (int i = 0; i < t.length; i++) {
            double y = sigmoidOut[i] * (1 - sigmoidOut[i]);
            t[i] = (E[i] - sigmoidOut[i]) * y;
        }
    }

    public static void setHiddenLayerT(double[] t, double[] next_t, double[] sigmoidOut, double[][] w) {
        for (int i = 0; i < t.length; i++) {
            double sum = 0;
            for (int j = 0; j < next_t.length; j++) {
                sum += w[i][j] * next_t[j];
            }
            double y = sigmoidOut[i] * (1 - sigmoidOut[i]);
            t[i] = sum * y;
        }
    }

    public static void setInputLayerT(double[] t, double[] next_t, double[][] w) {
        for (int i = 0; i < t.length; i++) {
            double sum = 0;
            for (int j = 0; j < next_t.length; j++) {
                sum += w[i][j] * next_t[j];
            }
            double y = 1;
            t[i] = sum * y;
        }
    }

    public static void setInputToPoolT(double[][][] pool_t, double[] input) {
        int index = 0;
        for (int i = 0; i < pool_t.length; i++) {
            for (int j = 0; j < pool_t[0].length; j++) {
                for (int k = 0; k < pool_t[0][0].length; k++) {
                    pool_t[i][j][k] = input[index++];
                }
            }
        }
    }

    public static void changeBpWeight(double[][] w, double studyRate, double[] next_t, double[] input) {
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[0].length; j++) {
                w[i][j] += (studyRate * next_t[j] * input[i]);
            }
        }
    }
}
