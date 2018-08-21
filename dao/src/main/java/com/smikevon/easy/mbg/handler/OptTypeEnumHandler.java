package com.smikevon.easy.mbg.handler;

import org.apache.ibatis.type.MappedTypes;

import com.smikevon.easy.mbg.custom.CustomEnumTypeHandler;
import com.smikevon.easy.model.enums.OptType;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
@MappedTypes(OptType.class)
public class OptTypeEnumHandler extends CustomEnumTypeHandler<OptType> {
    public OptTypeEnumHandler() {
        super(OptType.class);
    }
}
