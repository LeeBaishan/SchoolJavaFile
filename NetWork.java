package cnn;

public class NetWork {
    ConLayer conLayer1;
    PoolLayer poolLayer1;

    Residual residual1;
    ConLayer conLayer2;
    PoolLayer poolLayer2;

    Residual residual2;
    BPANN bpann;
    double studyRate;

    public NetWork(double studyRate) {
        this.studyRate = studyRate;
        conLayer1 = new ConLayer(32, 1, 5, 5, 1, 1, studyRate);
        poolLayer1 = new PoolLayer(2, 2, 1);
        residual1 = new Residual(1,32,28, 28, 2,0.5);
        conLayer2 = new ConLayer(64, 32, 5, 5, 1, 1, studyRate);
        poolLayer2 = new PoolLayer(2, 2, 1);
        residual2 = new Residual(32, 64, 14, 14, 2,0.5);
        bpann = new BPANN(3136, 1024, 10, studyRate);
    }

    public double[] forward(double[][][] pic) {
        double[][][] temp1 = Tool.getImage(pic, 1);
        double[][][] res1 = residual1.go(temp1);
        double[][][] con1 = conLayer1.go(temp1);
        double[][][] pool1 = poolLayer1.go(con1);

        //double[][][] temp2 = Tool.getImage(res1, pool1, 1);
        double[][][] res2 = residual2.go(res1);
        double[][][] con2 = this.conLayer2.go(pool1);
        //double[][][] con2 = conLayer2.go(temp2);
        double[][][] pool2 = poolLayer2.go(con2);


        double[][][] temp3 = Tool.getImage(res2, pool2);

        return bpann.go(temp3);
    }

    public void back(int e, int expect) {
        double[][][] pool2_t = bpann.turnBack(e, expect);
        double[][][] temp1 = Tool.getImage(pool2_t, 3);
        double[][][] con2_t = poolLayer2.turnBack(temp1);
        double[][][] pool1_t = conLayer2.turnBack(con2_t);
        double[][][] con1_t = poolLayer1.turnBack(pool1_t);
        conLayer1.turnBack(con1_t);
    }
}
