package com.smikevon.easy.mbg.handler;

import org.apache.ibatis.type.MappedTypes;

import com.smikevon.easy.mbg.custom.CustomEnumTypeHandler;
import com.smikevon.easy.model.enums.DeleteType;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
@MappedTypes(DeleteType.class)
public class DeleteTypeEnumHandler extends CustomEnumTypeHandler<DeleteType> {

    public DeleteTypeEnumHandler() {
        super(DeleteType.class);
    }
}
