package com.smikevon.easy.common.utils;

import lombok.Data;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
@Data
public class PageParam {

    /**
     * 设定默认获取第一页
     */
    private Integer pageNum = 1;

    /**
     * 设定默认分页大小为 20
     */
    private Integer pageSize = 20;
}
