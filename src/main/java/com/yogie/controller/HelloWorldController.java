package com.yogie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: prac-mq
 * @Date: 2019/12/17 21:37
 * @Author: Chenyogie
 * @Description:
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String get(){
        return "hello world!";
    }
}
