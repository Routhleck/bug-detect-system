package com.example.demo.Utils;/*
 author:@Antidote
 date:2022/10/1721:35
 
*/

import com.example.demo.common.knn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KNNtrain {
    public static String knn_train(String train_setName, String test_setName) {
        int k=5;
        List<Number> list=new ArrayList<Number>();
        int TP=0;
        int FP=0;
        int TN=0;
        int FN=0;
        String alg="KNN";
        knn knn_pre = new knn(k);
        double[][][] test_set = new double[0][][];
        double[][][] train_set = new double[0][][];

        try {
            train_set=Sets_process.readTrainSet("C:\\Users\\86186\\Documents\\GitHub\\bug-detect-system\\algorithm\\algorithm\\Datasets\\AEEEM\\csv\\"+train_setName);

            for (double[][] v : train_set) {
                knn_pre.fit(v[0], (int)v[1][0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            test_set=Sets_process.readTrainSet("C:\\Users\\86186\\Documents\\GitHub\\bug-detect-system\\algorithm\\algorithm\\Datasets\\AEEEM\\csv\\"+test_setName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 测试
        int[] result = new int[test_set.length];
        for (int i = 0; i < test_set.length; i++) {
            result[i] = knn_pre.predict(test_set[i][0]);
            // System.out.println("第" + (i+1) + "个测试样本，预测结果为：" + result[i] + "，实际结果为：" + (int)test_set[i][1][0]);
        }
        //System.out.println("测试完成");
        // 原本的标签
        int[] label = new int[test_set.length];
        for (int i = 0; i < test_set.length; i++) {
            label[i] = (int) test_set[i][1][0];
        }
        // 计算准确率
        double accuracy = 0;
        for (int i = 0; i < test_set.length; i++) {
            if (result[i] == label[i]) {
                accuracy++;
                if (result[i]==0){
                    TN++;
                }
                else if(result[i]==1){
                    TP++;
                }
            }
        }
        accuracy /= test_set.length;
        for (int i = 0; i < test_set.length; i++) {
            if (result[i]==1&label[i]==0){
                FN++;
            }
            else if (result[i]==0&label[i]==1)
                TP++;
        }
        list.add(TP);
        list.add(TN);
        list.add(FP);
        list.add(FN);
        list.add(accuracy);

        String json = "{\"alg\":\"" + alg + "\",\"TP\":\"" + TP + "\",\"TN\":\"" + TN + "\",\"FP\":\"" + FP + "\",\"FN\":\"" + FN + "\",\"accuracy\":\"" + accuracy + "\"}";
        return json;

    }
}
