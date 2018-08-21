package com.smikevon.easy.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyBatisColumn {

    /**
     * DB中存储值的字段
     */
    String fieldName() default "code";

}
