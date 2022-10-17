package com.example.demo.Utils;/*
 author:@Antidote
 date:2022/10/1721:50
 
*/

import com.example.demo.common.logisticRegression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogicRegression {
    public static String logic_train(String train_setName,String test_setName){
        double[][] test_set = new double[0][];
        double[][] train_set = new double[0][];
        List list1=new ArrayList();
        List<Number> list=new ArrayList<Number>();
        String alg;
        alg = "logicRegression";

        int TP=0;
        int FP=0;
        int TN=0;
        int FN=0;

        try {
            train_set=Sets_process.readTrainSet_logic("C:\\Users\\86186\\Documents\\GitHub\\bug-detect-system\\algorithm\\algorithm\\Datasets\\AEEEM\\csv\\"+train_setName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            test_set=Sets_process.readTrainSet_logic("C:\\Users\\86186\\Documents\\GitHub\\bug-detect-system\\algorithm\\algorithm\\Datasets\\AEEEM\\csv\\PDE.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 训练模型
        logisticRegression lr = new logisticRegression();
        // 训练集分为x和y
        double[][] x = new double[train_set.length][train_set[0].length - 1];
        double[] y = new double[train_set.length];
        for (int i = 0; i < train_set.length; i++) {
            for (int j = 0; j < train_set[0].length - 1; j++) {
                x[i][j] = train_set[i][j];
            }
            y[i] = train_set[i][train_set[0].length - 1];
        }

        System.out.println("debug");

        lr.train(x, y, 0.01, 1000);
        // 测试模型
        double[] predict = lr.predict(test_set);

        // 计算准确率
        double accuracy = lr.accuracy(predict, y);
        TP=Sets_process.CountingTP(predict,y);
        TN=Sets_process.CountingTN(predict,y);
        FP=Sets_process.CountingFP(predict,y);
        FN=Sets_process.CountingFN(predict,y);


        list1.add(TP);
        list1.add(TN);
        list1.add(FP);
        list1.add(FN);
        list1.add(accuracy);
        String json = "{\"alg\":\""+ alg +"\",\"TP\":\"" + TP + "\",\"TN\":\"" + TN + "\",\"FP\":\"" + FP + "\",\"FN\":\"" + FN + "\",\"accuracy\":\"" + accuracy + "\"}";

        return json;

    }
}
