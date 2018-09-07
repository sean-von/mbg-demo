package com.smikevon.easy.common.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.smikevon.easy.common.validator.EnumContainsValidator;

/**
 * Created by sean (smikevon@163.com) on 2018/9/7.
 */
@Constraint(validatedBy = EnumContainsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface EnumContains {

    /**
     * 枚举类
     */
    Class<? extends Enum> value();

    /**
     * 检查哪个字段
     */
    String checkField() default "code";


    String message() default "枚举类型不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EnumContains[] value();
    }

}
