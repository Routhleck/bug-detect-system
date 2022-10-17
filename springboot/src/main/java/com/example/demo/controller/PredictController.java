package com.example.demo.controller;/*

 author:@Antidote
 date:2022/10/1614:34
 
*/

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import com.example.demo.Utils.Sets_process;
import com.example.demo.common.Result;
import com.example.demo.common.knn;
import com.example.demo.common.logisticRegression;
import com.example.demo.entity.Predict;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/files")
public class PredictController {


    private static final String ip="http://localhost";
    @Value("${server.port}")
    private String port;
        //上传接口
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //获取原文件名称
        //前缀（唯一标识）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + originalFilename;
        FileUtil.writeBytes(file.getBytes(), rootFilePath);//将文件写入到上传的路径

        return Result.success(ip + ":" + port + "/files/" + flag);//返回结果url
    }
    //傻瓜接口
    @PostMapping("/predict")
    public JSONArray pre(@RequestBody Predict predict1) throws JSONException {
            List list=new ArrayList();

        if (predict1.getAlgorithm().equals("knn")){
            double[][][] test_set = new double[0][][];
            double[][][] train_set = new double[0][][];
            int k = 5;
            knn knn_pre = new knn(k);
            if (predict1.getTrain_sets().equals("JDT.csv")){
                try {
                    train_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/csv/JDT.csv");

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
                if (predict1.getTest_sets().equals("PDE.csv")){

                    try {
                            test_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/arff/PDE.arff");
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


                    for (int i = 0; i < result.length; i++) {
                        list.add(result[i]);
                    }
                    list.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;


                }
                else if (predict1.getTest_sets().equals("Lucene.csv")){
                        try {
                            test_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/arff/Lucene.csv");
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
                    list.add(accuracy);

                    for (int i = 0; i < result.length; i++) {
                        list.add(result[i]);
                    }
                    list.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;

                    };

            }
            else if(predict1.getTrain_sets().equals("PDE.csv")){
                try {
                    train_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/csv/PDE.csv");

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
                if (predict1.getTest_sets().equals("JDT.csv")){
                    try {
                        test_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/arff/JDT.csv");
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


                    for (int i = 0; i < result.length; i++) {
                        list.add(result[i]);
                    }
                    list.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;
                }
                else if (predict1.getTest_sets().equals("Lucene.csv")){
                    try {
                        test_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/arff/Lucene.csv");
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


                    for (int i = 0; i < result.length; i++) {
                        list.add(result[i]);
                    }list.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;

                };
            }
            else if(predict1.getTrain_sets().equals("Lucene.csv")){
                try {
                    train_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/csv/Lucene.csv");

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
                if (predict1.getTest_sets().equals("JDT.csv")){
                    try {
                        test_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/arff/JDT.csv");
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


                    for (int i = 0; i < result.length; i++) {
                        list.add(result[i]);
                    }
                    list.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;
                }
                else if (predict1.getTest_sets().equals("PDE.csv")){
                    try {
                        test_set=Sets_process.readTrainSet("algorithm/algorithm/Datasets/AEEEM/arff/PDE.arff");
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


                    for (int i = 0; i < result.length; i++) {
                        list.add(result[i]);
                    }
                    list.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;

                };

            }

        }
        if (predict1.getAlgorithm().equals("logicRegression")) {
            double[][] test_set = new double[0][];
            double[][] train_set = new double[0][];
            List list1=new ArrayList();

            if (predict1.getTrain_sets().equals("JDT.csv")){
                try {
                    train_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/csv/JDT.csv");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (predict1.getTest_sets().equals("PDE.csv")){
                    Map<String, String> map=new HashMap<>();
                    ObjectMapper mapper=new ObjectMapper();
                    try {
                        test_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/arff/PDE.arff");
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


                    for (int i = 0; i < predict.length; i++) {
                        list1.add(predict[i]);
                    }list1.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;
                }
                else if (predict1.getTest_sets().equals("Lucene.csv")){
                    try {
                        test_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/arff/Lucene.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }  // 训练模型
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

                    for (int i = 0; i < predict.length; i++) {
                        list1.add(predict[i]);
                    }
                    list1.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;
                };
            }
            else if(predict1.getTrain_sets().equals("PDE.csv")){
                try {
                    train_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/csv/PDE.csv");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (predict1.getTest_sets().equals("JDT.csv")){
                    try {
                        test_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/arff/JDT.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }  // 训练模型
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

                    for (int i = 0; i < predict.length; i++) {
                        list1.add(predict[i]);
                    }
                    list1.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;
                }
                else if (predict1.getTest_sets().equals("Lucene.csv")){
                    try {
                        test_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/arff/Lucene.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }  // 训练模型
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

                    for (int i = 0; i < predict.length; i++) {
                        list1.add(predict[i]);
                    }
                    list1.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;
                };
            }
            else if(predict1.getTrain_sets().equals("Lucene.csv")){
                try {
                    train_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/csv/Lucene.csv");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (predict1.getTest_sets().equals("JDT.csv")){
                    try {
                        test_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/arff/JDT.csv");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }  // 训练模型
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

                    for (int i = 0; i < predict.length; i++) {
                        list1.add(predict[i]);
                    }
                    list1.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;
                }
                else if (predict1.getTest_sets().equals("PDE.csv")){
                    try {
                        test_set=Sets_process.readTrainSet_logic("algorithm/algorithm/Datasets/AEEEM/arff/PDE.arff");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }  // 训练模型
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

                    for (int i = 0; i < predict.length; i++) {
                        list1.add(predict[i]);
                    }
                    list1.add(accuracy);
                    JSONArray ja = JSONArray.fromObject(list);
                    return ja;


                }
                else {
                    System.out.println("false");


                }
            }




        };
        return null;
    }
    //下载接口
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }

        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
    //傻瓜接口


}
