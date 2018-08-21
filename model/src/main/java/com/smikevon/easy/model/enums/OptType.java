package com.smikevon.easy.model.enums;

import com.smikevon.easy.model.annotation.MyBatisColumn;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@MyBatisColumn
public enum OptType {

    ADD(1, "NEW", "添加"),
    UPDATE(2, "UPDATE", "更新"),
    DELETE(3, "DELETE", "删除");

    private Integer code;
    private String type;
    private String description;

    OptType(Integer code, String type, String description) {
        this.code = code;
        this.type = type;
        this.description = description;
    }
}
