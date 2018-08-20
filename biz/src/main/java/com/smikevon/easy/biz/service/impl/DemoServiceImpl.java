package com.smikevon.easy.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smikevon.easy.biz.service.DemoService;
import com.smikevon.easy.mbg.mapper.OptLogMapper;
import com.smikevon.easy.model.entity.OptLog;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private OptLogMapper optLogMapper;

    @Override
    public void sayHello() {
        OptLog optLog = new OptLog();
        optLog.setId(System.nanoTime());
        optLog.setIp("");
        optLog.setItem(1L);
        optLog.setOptType(1);
        optLog.setKeywords("");
        optLog.setNewValue("new");
        optLog.setOldValue("old");
        optLog.setIsDelete(0);
        optLogMapper.insert(optLog);
        log.info("hello I'm service class");
    }
}
