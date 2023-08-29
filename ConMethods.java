package cnn;

import java.util.Random;

public class ConMethods {
    public static void setCoreWeight(double[][][][] core) {
        for (int i = 0; i < core.length; i++) {
            for (int c = 0; c < core[0].length; c++) {
                for (int j = 0; j < core[0][0].length; j++) {
                    for (int k = 0; k < core[0][0][0].length; k++) {
                        core[i][c][j][k] = 0.01 * new Random().nextGaussian();
                    }
                }
            }
        }
    }

    public static void setResult(double[][][][] conResult, double[][][] img, double[][][][] core, int s) {
        for (int i = 0; i < conResult.length; i++) {
            for (int c = 0; c < conResult[0].length; c++) {
                setCore(conResult[i][c], img[c], core[i][c], s);
            }
        }
    }

    public static void setResult(double[][][][] conResult, double[][][][] img, double[][][][] core, int s) {
        for (int i = 0; i < conResult.length; i++) {
            for (int c = 0; c < conResult[0].length; c++) {
                setCore(conResult[i][c], img[i][c], core[i][c], s);
            }
        }
    }

    public static void setSumConResult(double[][][] conResult, double[][][][] conTrueResult) {
        for (int i = 0; i < conTrueResult.length; i++) {
            for (int j = 0; j < conTrueResult[0][0].length; j++) {
                for (int k = 0; k < conTrueResult[0][0][0].length; k++) {
                    double sum = 0;
                    for (int c = 0; c < conTrueResult[0].length; c++) {
                        conTrueResult[i][c][j][k] = Tool.relu(conTrueResult[i][c][j][k]);
                        sum += conTrueResult[i][c][j][k];
                    }
                    conResult[i][j][k] = sum;
                }
            }
        }
    }

    public static void setCore(double[][] conResult, double[][] img, double[][] core, int s) {
        int length = img.length - core.length + 1;
        for (int imgY = 0; imgY < length; imgY += s) {
            for (int imgX = 0; imgX < length; imgX += s) {
                double sum = 0;
                for (int coreY = 0; coreY < core.length; coreY++) {
                    for (int coreX = 0; coreX < core[0].length; coreX++) {
                        sum += img[imgY + coreY][imgX + coreX] * core[coreY][coreX];
                    }
                }
                conResult[imgY / s][imgX / s] = sum;
            }
        }
    }

    public static void setConTrueResult_T(double[][][][] conTrueResult_t, double[][][] conResult_t,
                                          double[][][][] conTrueResult, double[][][] conResult) {
        for (int i = 0; i < conTrueResult_t.length; i++) {
            for (int c = 0; c < conTrueResult_t[0].length; c++) {
                for (int j = 0; j < conTrueResult_t[0][0].length; j++) {
                    for (int k = 0; k < conTrueResult_t[0][0][0].length; k++) {
                        if (conResult[i][j][k] != 0) {
                            conTrueResult_t[i][c][j][k] =  conTrueResult[i][c][j][k] / conResult[i][j][k] * conResult_t[i][j][k];
                        }
                    }
                }
            }
        }
    }

    public static void changeCoreWeight(double[][][][] core, double[][][][] core_t, double studyRate) {
        for (int i = 0; i < core.length; i++) {
            for (int c = 0; c < core[0].length; c++) {
                for (int j = 0; j < core[0][0].length; j++) {
                    for (int k = 0; k < core[0][0][0].length; k++) {
                        core[i][c][j][k] += studyRate * core_t[i][c][j][k];
                    }
                }
            }
        }
    }

    public static void setCore_180(double[][][][] con180, double[][][][] con) {
        for (int i = 0; i < con180.length; i++) {
            for (int c = 0; c < con180[0].length; c++) {
                for (int j = 0; j < con180[0][0].length; j++) {
                    for (int k = 0; k < con180[0][0][0].length; k++) {
                        con180[i][c][con180[0][0].length - 1 - j][con180[0][0][0].length - 1 - k] = con[i][c][j][k];
                    }
                }
            }
        }
    }

    public static void setSumResult(double[][][] conResult, double[][][][] conTrueResult) {
        for (int c = 0; c < conTrueResult[0].length; c++) {
            for (int j = 0; j < conTrueResult[0][0].length; j++) {
                for (int k = 0; k < conTrueResult[0][0][0].length; k++) {
                    for (int i = 0; i < conTrueResult.length; i++) {
                        conResult[c][j][k] += conTrueResult[i][c][j][k];
                    }
                }
            }
        }
    }
}