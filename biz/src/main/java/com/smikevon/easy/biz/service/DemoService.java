package com.smikevon.easy.biz.service;

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

}
