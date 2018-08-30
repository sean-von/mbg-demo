package com.smikevon.easy.mbg.mapper;

import org.apache.ibatis.annotations.Param;

import com.smikevon.easy.model.entity.OptLog;
import com.smikevon.easy.model.entity.OptLogExample;

/**
 * 开启二级缓存
 */
public interface OptLogMapper extends BaseMapper<OptLog, OptLogExample> {

    OptLog getById(@Param("id") Long id);

}