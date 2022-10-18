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

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getTrain_sets() {
        return train_sets;
    }

    public void setTrain_sets(String train_sets) {
        this.train_sets = train_sets;
    }

    public String getTest_sets() {
        return test_sets;
    }

    public void setTest_sets(String test_sets) {
        this.test_sets = test_sets;
    }

    private String train_sets;
    private String test_sets;

    @Override
    public String toString(){
        return "algorithm : " + algorithm + " train_sets: " + train_sets + " test_sets :" + test_sets;
    }
}
