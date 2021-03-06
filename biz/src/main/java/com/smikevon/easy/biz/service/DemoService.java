package com.smikevon.easy.biz.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.smikevon.easy.common.utils.PageParam;
import com.smikevon.easy.model.entity.OptLog;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
public interface DemoService {

    void sayHello();

    void rollback();

    PageInfo<OptLog> getLogByPage(PageParam pageParam);

    Void clearPage();

    OptLog getLog(Long logId);

    OptLog getById(Long logId);

    List<OptLog> getAll();

}
