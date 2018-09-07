package com.smikevon.easy.common.vo;

import com.smikevon.easy.common.validator.annotation.EnumContains;
import com.smikevon.easy.model.enums.OptType;

import lombok.Data;

/**
 * Created by sean (smikevon@163.com) on 2018/9/7.
 */
@Data
public class SearchParam {

    private String name;

    @EnumContains(value = OptType.class, message = "操作类型不存在")
    private Integer optType;

}
