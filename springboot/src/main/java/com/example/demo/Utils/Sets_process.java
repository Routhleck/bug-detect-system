package com.example.demo.Utils;/*
 author:@Antidote
 date:2022/10/1615:46
 
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sets_process { public static double[][][] readTrainSet(String path) throws IOException {
    // 读取csv文件
    BufferedReader br = new BufferedReader(new FileReader(path));
    // 读取第一行，获取列数
    String line = br.readLine();
    String[] firstLine = line.split(",");
    int colNum = firstLine.length;
    // 读取第一列，获取行数
    int rowNum = 0;
    while ((line = br.readLine()) != null) {
        rowNum++;
    }
    // 读取数据
    br = new BufferedReader(new FileReader(path));
    double[][][] train_set = new double[rowNum][2][colNum];
    line = br.readLine();
    for (int i = 1; i < rowNum; i++) {
        line = br.readLine();
        String[] temp = line.split(",");
        for (int j = 0; j < colNum - 1; j++) {
            train_set[i][0][j] = Double.parseDouble(temp[j]);
        }
        if(temp[colNum - 1].equals("buggy")){
            train_set[i][1][0] = 1;
        }else{
            train_set[i][1][0] = 0;
        }
    }
    return train_set;
}

    public static double[][] readTrainSet_logic(String path) throws IOException {
        // 读取训练集
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
        String[] firstLine = line.split(",");
        int colNum = firstLine.length;
        int rowNum = 0;
        while ((line = br.readLine()) != null) {
            rowNum++;
        }
        br = new BufferedReader(new FileReader(path));
        double[][] train_set = new double[rowNum][colNum];
        line = br.readLine();
        // 最后一列为标签, 令buggy为1，clean为0
        for (int i = 0; i < rowNum; i++) {
            line = br.readLine();
            String[] temp = line.split(",");
            for (int j = 0; j < colNum - 1; j++) {
                train_set[i][j] = Double.parseDouble(temp[j]);
            }
            if(temp[colNum - 1].equals("buggy")){
                train_set[i][colNum - 1] = 1;
            }else{
                train_set[i][colNum - 1] = 0;
            }
        }
        return train_set;
    }
}
