package cnn;

public class PoolMethods {
    public static void setPoolResult(double[][][] poolResult, double[][][] img, int size, int s) {
        for (int i = 0; i < poolResult.length; i++) {
            setPoolResult(poolResult[i], img[i], size, s);
        }
    }

    public static void setPoolResult(double[][] poolResult, double[][] img, int size, int s) {
        for (int imgY = 0; imgY < img.length - size + 1; imgY += s) {
            for (int imgX = 0; imgX < img[0].length - size + 1; imgX += s) {
                double max = Integer.MIN_VALUE;
                for (int y = imgY; y < imgY + size; y++) {
                    for (int x = imgX; x < imgX + size; x++) {
                        max = Math.max(max, img[y][x]);
                    }
                }
                poolResult[imgY / s][imgX / s] = max;
            }
        }
    }

    public static void setBackPool_T(double[][][] t, double[][][] pic, double[][][] pool_t, int pool_size, int s) {
        for (int i = 0; i < t.length; i++) {
            setBackPool_T(t[i], pic[i], pool_t[i], pool_size, s);
        }
    }

    public static void setBackPool_T(double[][] t, double[][] img, double[][] pool_t, int size, int s) {
        for (int imgY = 0; imgY < img.length - size + 1; imgY += s) {
            for (int imgX = 0; imgX < img[0].length - size + 1; imgX += s) {
                int row = imgY;
                int column = imgX;
                for (int y = imgY; y < imgY + size; y++) {
                    for (int x = imgX; x < imgX + size; x++) {
                        if (img[y][x] > img[row][column]) {
                            row = y;
                            column = x;
                        }
                    }
                }
                t[row][column] = pool_t[row / s][column / s];
            }
        }
    }
}
