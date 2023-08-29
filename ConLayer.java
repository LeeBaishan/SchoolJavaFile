package cnn;

public class ConLayer {
    //前向所需数组
    double[][][] inputImg;
    double[][][] inputPadImg;
    double[][][][] core;
    double[][][][] coreImg;             //四维卷积输出
    double[][][] outImg;                //四维转成三维方法1（通道数叠加，个数不变）

    //反向所需数组
    double[][][] inputImg_t;
    double[][][] inputPadImg_t;           //四维转成三维方法2（个数叠加，通道数不变）
    double[][][][] corePadMid_t;        //回传真实四维卷积梯度
    double[][][][] core_180;

    double[][][][] core_t;

    double[][][][] corePadImg_t;        //真实卷积结果填充梯度
    double[][][][] coreImg_t;           //真实卷积结果梯度
    double[][][] outImg_t;            //回传梯度时的输入梯度

    int s;
    int p;
    double studyRate;

    public ConLayer(int number, int channel, int height, int width, int stride, int pad, double studyRate) {
        core = new double[number][channel][height][width];
        ConMethods.setCoreWeight(core);
        this.s = stride;
        this.p = pad;
        this.studyRate = studyRate;
    }

    public double[][][] go(double[][][] inputImgTemp) {
        inputImg = inputImgTemp;

        inputPadImg = new double[inputImg.length][inputImg[0].length + 2 * p][inputImg[0].length + 2 * p];
        Tool.setImgPad(inputPadImg, inputImg, p);

        int picSize = Tool.getPicSize(inputImg[0].length, core[0][0].length, p, s);
        coreImg = new double[core.length][core[0].length][picSize][picSize];

        ConMethods.setResult(coreImg, inputPadImg, core, s);

        outImg = new double[coreImg.length][coreImg[0][0].length][coreImg[0][0][0].length];
        ConMethods.setSumConResult(outImg, coreImg);

        return outImg;
    }

    public double[][][] turnBack(double[][][] outImgTemp_t) {
        outImg_t = outImgTemp_t;

        coreImg_t = new double[coreImg.length][coreImg[0].length][coreImg[0][0].length][coreImg[0][0].length];
        ConMethods.setConTrueResult_T(coreImg_t, outImg_t, coreImg, outImg);  //√

        int padSize = Tool.getPadSize(inputPadImg[0].length, core[0][0].length, coreImg[0][0].length, s); //√

        corePadImg_t = new double[coreImg_t.length][coreImg_t[0].length]
                [coreImg_t[0][0].length + 2 * padSize][coreImg_t[0][0].length + 2 * padSize];  //√

        Tool.setImgPad(corePadImg_t, coreImg_t, padSize);  //√

        core_180 = new double[core.length][core[0].length][core[0][0].length][core[0][0][0].length];
        ConMethods.setCore_180(core_180, core);  //√

        //反向传递卷积运算后得到的四维通道梯度
        int conPicSize = Tool.getPicSize(coreImg_t[0][0].length, core_180[0][0].length, padSize, s);
        corePadMid_t = new double[core_180.length][core_180[0].length][conPicSize][conPicSize];   //√

        ConMethods.setResult(corePadMid_t, corePadImg_t, core_180, s);  //√

        inputPadImg_t = new double[inputPadImg.length][inputPadImg[0].length][inputPadImg[0][0].length];
        ConMethods.setSumResult(inputPadImg_t, corePadMid_t);   //√

        //得到卷积层原图梯度
        inputImg_t = new double[inputImg.length][inputImg[0].length][inputImg[0][0].length];
        Tool.setBackImgPad(inputImg_t, inputPadImg_t, p);   //√

        //更新权重
        core_t = new double[core.length][core[0].length][core[0][0].length][core[0][0][0].length];
        ConMethods.setResult(core_t, inputPadImg, coreImg_t, 1);  //√

        ConMethods.changeCoreWeight(core, core_t, studyRate);   //

        return inputImg_t;
    }
}
