package com.smikevon.easy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smikevon.easy.biz.service.DemoService;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/hello")
    public String index() {
        demoService.sayHello();
        return "Hello World";
    }

}
