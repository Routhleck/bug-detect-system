package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("record")
@Data
public class Record {
    // 这里对应UserMapper（继承自Mybatis plus中的BaseMapper的组件），默认标识值是id并且默认为递增
    private Integer recordID;
    private Integer userID;
    private Integer modelID;
    private String  accuracy;
}

