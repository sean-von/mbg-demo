package com.smikevon.easy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.smikevon.easy.biz.service.DemoService;
import com.smikevon.easy.common.utils.PageParam;
import com.smikevon.easy.common.utils.Result;
import com.smikevon.easy.model.entity.OptLog;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("hello")
    public String index() {
        demoService.sayHello();
        return "Hello World";
    }

    @GetMapping("list")
    public Result<PageInfo<OptLog>> list(PageParam pageParam) {
        return Result.success(demoService.getLogByPage(pageParam));
    }

}
