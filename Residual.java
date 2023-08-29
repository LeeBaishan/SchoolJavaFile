package cnn;

public class Residual {
    //前向
    double[][][] inputImage;

    double[][][] canImage;
    double[][][] outImage;



    int number;
    int channel;
    int height;
    int width;

    int stride;
    double weight;

    public Residual(int number, int channel, int height, int width, int stride, double weight) {
        this.number = number;
        this.channel = channel;
        this.height = height;
        this.width = width;
        this.stride = stride;
        this.weight = weight;

        this.inputImage = new double[number][height][height];
        this.canImage = new double[channel][height][height];
        this.outImage = new double[channel][height/2][width/2];
    }

    public double[][][] go(double[][][] inputImage) {
        this.inputImage = inputImage;

        ResidualMethods.setCanImage(canImage, inputImage, channel);//残差层中分化图像数据

        outImage = new double[canImage.length][canImage[0].length/2][canImage[0][0].length/2];
        ResidualMethods.setOutImage(outImage, canImage, stride);

        return outImage;
    }

}
