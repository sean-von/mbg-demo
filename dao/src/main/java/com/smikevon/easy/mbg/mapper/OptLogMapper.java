package com.smikevon.easy.mbg.mapper;

import com.smikevon.easy.model.entity.OptLog;
import com.smikevon.easy.model.entity.OptLogExample;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
public interface OptLogMapper extends BaseMapper<OptLog, OptLogExample> {

    @Override
    int insert(OptLog record);
}
