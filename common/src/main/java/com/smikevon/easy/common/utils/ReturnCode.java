package com.smikevon.easy.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
@Getter
@ToString
@AllArgsConstructor
public enum ReturnCode {

    //通用错误码 1-1000
    SUCCESS(0, "成功"),
    INVALID_SIGN(1, "非法请求"),
    PARAM_TYPE_ERROR(4, "参数类型错误"),
    PARAM_ERROR(5, "参数错误"),
    REDIS_ERROR(6, "REDIS错误"),
    SMS_SEND_ERROR(7, "短信发送失败，请稍后再试"),
    QI_NIU_STORAGE_ERROR(8, "存储失败，请稍后再试"),
    SMS_CODE_ERROR(9, "验证码错误或失效"),
    NO_RIGHT(10, "没有权限或非法操作"),
    EMAIL_SEND_ERROR(11, "邮箱格式不正确"),
    REG_FORBIDDEN(12, "账号或设备被封"),
    DEVICE_UNIQUE(13, "同一个设备只能注册一次");

    private int code;
    private String desc;

}
