package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Model;
import com.example.demo.mapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Resource
    ModelMapper ModelMapper;

    @PostMapping
    public Result<?> save(@RequestBody Model Model) {
        ModelMapper.insert(Model);
        return Result.success();
    }

    // 更新用户\信息操作
    @PutMapping
    public Result<?> update(@RequestBody Model Model) {
        ModelMapper.updateById(Model);
        return Result.success();
    }

    // 删除用户操作
    @DeleteMapping("/{modelID}")
    public Result<?> delete(@PathVariable Long modelID) {
        ModelMapper.deleteById(modelID);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 分页模糊查询like即Model::getNickName, search意为getNickname与search相同；Wrappers相当于Where
        LambdaQueryWrapper<Model> wrapper = Wrappers.<Model>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Model::getModelID, search);
            wrapper.like(Model::getUsername, search);
            wrapper.like(Model::getUserID, search);
            wrapper.like(Model::getAlgorithm, search);
            wrapper.like(Model::getDate, search);
            wrapper.like(Model::getTestData, search);
            wrapper.like(Model::getTrainDataset, search);
        }
        Page<Model> ModelPage = ModelMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(ModelPage);
    }
}
