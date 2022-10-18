package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Record;
import com.example.demo.mapper.RecordMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/record")
public class HistoryController {
    
    @Resource
    RecordMapper RecordMapper;
    
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Record> wrapper = Wrappers.<Record>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Record::getId,search);
        }
        Page<Record> RecordPage=RecordMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(RecordPage);
    }
}
