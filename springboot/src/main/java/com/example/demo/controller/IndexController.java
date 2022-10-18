package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: springboot
 * @description: controler
 * @author: 20301037_Routhleck
 * @create: 2022-10-19 00:06
 **/
@Controller
public class IndexController {
    @GetMapping("/index")
    public String indexStart() {
        return "index";
    }
}
