package com.smikevon.easy.model.enums;

import com.smikevon.easy.model.annotation.MyBatisColumn;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@MyBatisColumn
public enum DeleteType {

    NOT_DELETED(0, "未删除"),
    DELETED(1, "已删除");

    private Integer code;
    private String description;

    DeleteType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

}
