package com.smikevon.easy.web.handler;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.smikevon.easy.common.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sean (smikevon@163.com) on 2018/9/7.
 */
@Slf4j
@Order(1)
@ControllerAdvice(annotations = RestController.class)
@EnableWebMvc
public class MbgExceptionHandler {

    /**
     * 校验未通过异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<Boolean> exceptionHandler(BindException e, HttpServletResponse response) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            log.error(error.getField() + ":" + error.getDefaultMessage(), e);
        }
        FieldError firstError = fieldErrors.get(NumberUtils.INTEGER_ZERO);
        String message = String.join(":", firstError.getField(), firstError.getDefaultMessage());
        return Result.fail(-1, message, null);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public Result<Boolean> exceptionHandler(SQLException e) {
        log.error("SQL Exception Throw", e);
        return Result.fail(-1, "后台数据库错误", null);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Boolean> exceptionHandler(Exception e) {
        log.error("Other Exception Throw", e);
        return Result.fail(-1, e.getMessage(), null);
    }

}
