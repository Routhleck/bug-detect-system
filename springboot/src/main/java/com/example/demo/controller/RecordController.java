package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.common.knn;
import com.example.demo.common.logisticRegression;
import com.example.demo.entity.Record;
import com.example.demo.mapper.RecordMapper;
import com.example.demo.utils.Sets_process;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/predict")
public class RecordController {
    @Resource
    RecordMapper RecordMapper;
    @PostMapping
    public Result<?> save(@RequestBody Record Record){
        String testFile = Record.getFile();
        //String uploadFile = str.replaceAll("http://localhost:9090/files/","");//获取上传的文件名

        String trainFile = Record.getTrainDataset(); //训练数据文件名称

        if (Record.getAlgorithm().equals("KNN")){
            double[][][] test_set = new double[0][][];
            double[][][] train_set = new double[0][][];
            int k = 5;
            knn knn_pre = new knn(k);
            try {
                train_set= Sets_process.readTrainSet(trainFile);

                // 训练
                //System.out.println("开始训练");
                for (double[][] v : train_set) {
                    knn_pre.fit(v[0], (int)v[1][0]);
                }
                //System.out.println("训练完成");
                //System.out.println("开始测试");

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                test_set=Sets_process.readTrainSet(testFile);
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
                }
            }
            accuracy /= test_set.length;

            Record.setAccuracy(accuracy);

//            RecordMapper.insert(Record);
//            return Result.success();


        }else{

            double[][] test_set = new double[0][];
            double[][] train_set = new double[0][];

            try {
                train_set=Sets_process.readTrainSet_logic(trainFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                test_set=Sets_process.readTrainSet_logic(testFile);
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

            double[][] x_test = new double[test_set.length][test_set[0].length - 1];
            double[] y_test = new double[test_set.length];
            for (int i = 0; i < test_set.length; i++) {
                for (int j = 0; j < test_set[0].length - 1; j++) {
                    x_test[i][j] = test_set[i][j];
                }
                y_test[i] = test_set[i][test_set[0].length - 1];
            }

            System.out.println("debug");

            lr.train(x, y, 0.01, 1000);
            // 测试模型
            double[] predict = lr.predict(x_test);

            // 计算准确率
            double accuracy = lr.accuracy(predict, y_test);
            //accuracy = lr.accuracy(predict, y);
            Record.setAccuracy(accuracy);
//            list1.add(accuracy);
//
//            for (int i = 0; i < predict.length; i++) {
//                list1.add(predict[i]);
//            }
//            JSONArray ja = JSONArray.fromObject(list);
//            return ja;
        }
        RecordMapper.insert(Record);
        return Result.success(Record.getAccuracy());
    }

    @GetMapping
    public Result<?> find(){
        LambdaQueryWrapper<Record> wrapper = Wrappers.lambdaQuery();
        List<Record> pre = RecordMapper.selectList(wrapper);
        return Result.success(pre);
    }

}
