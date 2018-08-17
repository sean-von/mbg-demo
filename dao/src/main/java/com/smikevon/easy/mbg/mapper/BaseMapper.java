package com.smikevon.easy.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Created by sean (smikevon@163.com) on 2018/8/17.
 */
public interface BaseMapper<ENTITY, EXAMPLE> {

    long countByExample(EXAMPLE example);

    int deleteByExample(EXAMPLE example);

    int deleteByPrimaryKey(Long id);

    int insert(ENTITY record);

    int insertSelective(ENTITY record);

    List<ENTITY> selectByExample(EXAMPLE example);

    ENTITY selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ENTITY record, @Param("example") EXAMPLE example);

    int updateByExample(@Param("record") ENTITY record, @Param("example") EXAMPLE example);

    int updateByPrimaryKeySelective(ENTITY record);

    int updateByPrimaryKey(ENTITY record);
}
