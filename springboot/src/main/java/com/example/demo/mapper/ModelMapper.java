package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Model;

// 实际上是mybatis plus包的使用（其中的Mapper工具）
// Mapper的作用是操作数据库
public interface ModelMapper extends BaseMapper<Model> {
}
