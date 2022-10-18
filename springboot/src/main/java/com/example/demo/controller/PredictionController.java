package com.example.demo.controller;/*
 author:@Antidote
 date:2022/10/1721:46
 
*/

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import com.example.demo.Utils.KNNtrain;
import com.example.demo.Utils.LogicRegression;
import com.example.demo.entity.Predict;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
@Controller
@RestController
@RequestMapping("/file")
public class PredictionController {
    private static final String ip="http://localhost";
    @Value("${server.port}")
    private String port;
    @PostMapping ("/prediction")
    public String pre(@RequestBody Predict predict1) throws JSONException {
        System.out.println(predict1.toString());
        if (predict1.getAlgorithm().equals("KNN")){
            String json= KNNtrain.knn_train(predict1.getTrain_sets(),predict1.getTest_sets());
            return json;
        }
        if (predict1.getAlgorithm().equals("逻辑回归")) {
            String json= LogicRegression.logic_train(predict1.getTrain_sets(),predict1.getTest_sets());
            return json;
    }
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
