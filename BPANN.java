package cnn;

public class BPANN {
    double[][][] poolPic;
    double[] input;
    double[][] w1;
    double[] hidden1;
    double[][] w2;
    double[] out;

    double[][][] poolPic_t;
    double[] input_t;
    double[] hidden1_t;
    double[] out_t;
    double[] E;

    double studyRate;

    public BPANN(int input_size, int hidden1_size, int out_size, double studyRate) {
        w1 = new double[input_size][hidden1_size];
        BPMethods.setBPWeight(w1);
        w2 = new double[hidden1_size][out_size];
        BPMethods.setBPWeight(w2);

        input = new double[input_size];
        input_t = new double[input_size];
        hidden1 = new double[hidden1_size];
        hidden1_t = new double[hidden1_size];
        out = new double[out_size];
        out_t = new double[out_size];
        E = new double[out_size];

        this.studyRate = studyRate;
    }

    public double[] go(double[][][] trueInputTemp) {
        setBPArrays();

        poolPic = trueInputTemp;

        poolPic_t = new double[poolPic.length][poolPic[0].length][poolPic[0][0].length];

        BPMethods.setInputLayer(input, poolPic);

        BPMethods.setHiddenLayer(hidden1, input, w1);

        BPMethods.setHiddenLayer(out, hidden1, w2);

        return out;
    }

    public double[][][] turnBack(int aimIndex, int expectValue) {
        E[aimIndex] = expectValue;

        BPMethods.setOutLayerT(out_t, out, E);

        BPMethods.setHiddenLayerT(hidden1_t, out_t, hidden1, w2);

        BPMethods.setInputLayerT(input_t, hidden1_t, w1);

        BPMethods.setInputToPoolT(poolPic_t, input_t);

        BPMethods.changeBpWeight(w1, studyRate, hidden1_t, input);

        BPMethods.changeBpWeight(w2, studyRate, out_t, hidden1);

        return poolPic_t;
    }

    public void setBPArrays() {
        input = new double[input.length];
        input_t = new double[input.length];
        hidden1 = new double[hidden1.length];
        hidden1_t = new double[hidden1.length];
        out = new double[out.length];
        out_t = new double[out.length];
        E = new double[out.length];
    }

}
