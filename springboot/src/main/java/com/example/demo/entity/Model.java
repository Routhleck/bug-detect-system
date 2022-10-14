package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("model")
@Data
public class Model {
    // 这里对应UserMapper（继承自Mybatis plus中的BaseMapper的组件），默认标识值是id并且默认为递增
    private Integer modelID;
    private Integer userID;
    private String username;
    private String trainDataset;
    private String testData;
    private String algorithm;
    private Integer isCountable;
    private Date date;
}
