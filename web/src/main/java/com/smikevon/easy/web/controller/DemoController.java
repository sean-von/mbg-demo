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
import com.smikevon.easy.web.config.CommonConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@Slf4j
@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;
    @Autowired
    private CommonConfig commonConfig;

    @GetMapping("hello")
    public String index() {
        demoService.sayHello();
        return "Hello World";
    }

    @GetMapping("rollback")
    public String rollback() {
        demoService.rollback();
        return "Hello World";
    }

    @GetMapping("list")
    public Result<PageInfo<OptLog>> list(PageParam pageParam) {
        return Result.success(demoService.getLogByPage(pageParam));
    }

    @GetMapping("get")
    public Result<OptLog> get(Long id) {
        return Result.success(demoService.getById(id));
    }

    @GetMapping("clear")
    public Result<Void> clear() {
        return Result.success(demoService.clearPage());
    }

    @GetMapping("info")
    public Result<OptLog> info(Long logId) {
        return Result.success(demoService.getLog(logId));
    }

}
