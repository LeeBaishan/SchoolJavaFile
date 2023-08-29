package cnn;

import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        double studyRate = 0.01;

        NetWork netWork = new NetWork(studyRate);
        int count = 1;
        //训练
        for (int rounds = 0; rounds < 5; rounds++) {
            for (int label = 0; label < 100; label++) {
                for (int picNumber = 0; picNumber < 10; picNumber++) {
                    double[] out = netWork.forward(Tool.getGreyValue(/*存储数据的绝对路径*/
                            "D:\\MrLee\\代码和实验室\\lab\\cnn\\MINST_train\\" + picNumber + "\\" + picNumber + "." + label + ".jpg"));
                    netWork.back(picNumber, 1);
                    System.out.println(Arrays.toString(out));
                    System.out.printf("%6d", count++);
                    System.out.printf("%6d", picNumber);
                    System.out.println();
                }
            }
        }

        //测试
        int countTest = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                double[] outTest = netWork.forward(Tool.getGreyValue(/*存储数据的绝对路径*/
                        "D:\\MrLee\\代码和实验室\\lab\\cnn\\MINST_test\\" + i + "\\" + i + "." + (j + 3600) + ".jpg"));
                int value = getArraysMax(outTest);
                if (value == i) {
                    countTest++;
                }
            }
        }
        System.out.println("学习率是 :  " + studyRate);
        System.out.println("识别率是 :  " + (countTest * 1.0 / 10) + "%");
    }

    private static int getArraysMax(double[] out) {
        double max = 0;
        int index = 0;
        for (int i = 0; i < out.length; i++) {
            if (Math.round(out[i]) == 1 && Math.round(out[i]) > max) {
                max = Math.round(out[i]);
                index = i;
            }
        }
        return index;
    }
}
