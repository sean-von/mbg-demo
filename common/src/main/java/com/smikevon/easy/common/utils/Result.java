package com.smikevon.easy.common.utils;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
@Data
public class Result<DTO> implements Serializable {

    private static final long serialVersionUID = 8559760068684726544L;

    private int code;
    private DTO data;
    private String desc;
    private String reqId;

    public static Result success() {
        return success(null);
    }

    public static <DTO> Result<DTO> success(DTO object) {
        Result<DTO> result = new Result<>();
        result.desc = ReturnCode.SUCCESS.getDesc();
        result.data = object;
        result.code = ReturnCode.SUCCESS.getCode();
        return result;
    }

    public static <T> Result<T> fail(ReturnCode returnCode) {
        return fail(returnCode, null);
    }

    public static <T> Result<T> fail(ReturnCode returnCode, T flag) {
        return fail(returnCode.getCode(), returnCode.getDesc(), flag);
    }

    public static <T> Result<T> fail(int code, String desc) {
        Result<T> result = new Result<>();
        result.code = code;
        result.desc = desc;
        return result;
    }

    public static <T> Result<T> fail(int code, String desc, T flag) {
        Result<T> result = new Result<>();
        result.code = code;
        result.desc = desc;
        result.data = flag;
        return result;
    }
}
