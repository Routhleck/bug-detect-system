package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookMapper BookMapper;

    @PostMapping
    public Result<?> save(@RequestBody Book Book) {
        BookMapper.insert(Book);
        return Result.success();

    }

    @PutMapping
    public Result<?> update(@RequestBody Book Book) {
        BookMapper.updateById(Book);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> detele(@PathVariable Long id) {
        BookMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Book::getName,search);
        }
        Page<Book> BookPage=BookMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(BookPage);
    }
}
