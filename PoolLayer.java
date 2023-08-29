package cnn;

public class PoolLayer {
    //前向所需数组
    double[][][] inputImg;
    double[][][] inputPadImg;
    double[][][] outImg;

    //反向所需数组
    double[][][] inputImg_t;
    double[][][] inputPadImg_t;
    double[][][] outImg_t;

    int s;
    int p;
    int size;                   //池化尺寸

    public PoolLayer(int size, int stride, int pad) {
        this.size = size;
        this.s = stride;
        this.p = pad;
    }

    public double[][][] go(double[][][] inputImgTemp) {
        inputImg = inputImgTemp;  //√

        inputPadImg = new double[inputImg.length][inputImg[0].length + 2 * p][inputImg[0][0].length + 2 * p];  //√
        Tool.setImgPad(inputPadImg, inputImg, p);  //√

        int picSize = Tool.getPicSize(inputImg[0].length, size, p, s);  //√

        outImg = new double[inputImg.length][picSize][picSize];     //√
        PoolMethods.setPoolResult(outImg, inputPadImg, size, s);    //√

        return outImg;
    }

    public double[][][] turnBack(double[][][] outImgTemp_t) {
        outImg_t = outImgTemp_t;

        inputPadImg_t = new double[inputPadImg.length][inputPadImg[0].length][inputPadImg[0][0].length];  //√
        PoolMethods.setBackPool_T(inputPadImg_t, inputPadImg, outImg_t, size, s); //√

        inputImg_t = new double[inputImg.length][inputImg[0].length][inputImg[0][0].length];
        Tool.setBackImgPad(inputImg_t, inputPadImg_t, p); //√

        return inputImg_t;
    }
}
