package com.example.demo.entity;/*
 author:@Antidote
 date:2022/10/1716:23
 
*/

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("Predict")
@Data
public class Predict {
    @TableId(type = IdType.AUTO)
    private String algorithm;
    private String train_sets;
    private String test_sets;
}
