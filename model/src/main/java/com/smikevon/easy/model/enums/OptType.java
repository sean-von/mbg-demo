package com.smikevon.easy.model.enums;

import java.lang.reflect.Field;

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

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field checkField = OptType.class.getDeclaredField("code");
        System.out.println(checkField.getType());
        if (!checkField.getType().isAssignableFrom(Integer.class)) {
            throw new RuntimeException("检查字段应整型");
        }

        checkField.setAccessible(true);
        for (Enum item : OptType.class.getEnumConstants()) {
            System.out.println(checkField.get(item));
        }

    }

}
