package cnn;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tool {
    static double relu(double sum) {
        return sum > 0 ? sum : 0;
    }

    static double sigmoid(double sum) {
        return 1.0 / (1 + Math.exp(-sum));
    }

    static int getPicSize(int n, int f, int p, int s) {
        return (n + 2 * p - f) / s + 1;
    }

    static int getPadSize(int n, int f, int coreImgLength, int s) {
        return (s * (n - 1) + f - coreImgLength) / 2;
    }

    static void setImgPad(double[][][] imgPad, double[][][] img, int p) {
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
                for (int k = 0; k < img[0][0].length; k++) {
                    imgPad[i][j + p][k + p] = img[i][j][k];
                }
            }
        }
    }

    static void setImgPad(double[][][][] imgPad, double[][][][] img, int p) {
        for (int i = 0; i < imgPad.length; i++) {
            setImgPad(imgPad[i], img[i], p);
        }
    }

    static void setBackImgPad(double[][][] imgPad, double[][][] img, int p) {
        for (int i = 0; i < imgPad.length; i++) {
            for (int j = 0; j < imgPad[0].length; j++) {
                for (int k = 0; k < imgPad[0][0].length; k++) {
                    imgPad[i][j][k] = img[i][j + p][k + p];
                }
            }
        }
    }

    public static double[][][] getGreyValue(String file) throws IOException {
        // 创建BufferedImage对象
        BufferedImage image = ImageIO.read(Files.newInputStream(Paths.get(file)));
        int iw = image.getWidth();
        int ih = image.getHeight();

        // 输入灰度值体积
        double[][][] volume = new double[1][iw][ih];
//        System.out.println("图片长为：" + volume.length);
//        System.out.println("图片宽为：" + volume[0].length);
        for (int i = 0; i < iw; i++) {
            for (int j = 0; j < ih; j++) {
                int pixel = image.getRGB(i, j);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;
                volume[0][j][i] = (red + green + blue) / 3.0;
            }
        }
        return volume;
    }

    public static double[][][] getImage(double[][][] pic, double weight) {
        double[][][] temp = new double[pic.length][pic[0].length][pic[0][0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                for (int k = 0; k < temp[0][0].length; k++) {
                    temp[i][j][k] = pic[i][j][k] * weight;
                }
            }
        }
        return temp;
    }

    public static double[][][] getImage(double[][][] pic1,double[][][]pic2, double weight) {
        double[][][] temp = new double[pic1.length][pic1[0].length][pic1[0][0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                for (int k = 0; k < temp[0][0].length; k++) {
                    temp[i][j][k] = (pic1[i][j][k] + pic2[i][j][k]) * weight;
                }
            }
        }
        return temp;
    }

    public static double[][][] getImage(double[][][] pic1, double[][][] pic2) {
        double[][][] temp = new double[pic1.length][pic1[0].length][pic1[0][0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                for (int k = 0; k < temp[0][0].length; k++) {
                    temp[i][j][k] = (pic1[i][j][k] + pic2[i][j][k]);
                }
            }
        }
        return temp;
    }

}
