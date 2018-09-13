package com.smikevon.easy.web.interceptor;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smikevon.easy.common.utils.Constant;
import com.smikevon.easy.common.utils.Delimiters;
import com.smikevon.easy.common.utils.IdWorker;
import com.smikevon.easy.common.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by fengxiao (smikevon@163.com) on 2018/8/26.
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class ReqLogAspect {

    private static final String REQUEST_LOG_KEY = "request_log_id";
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String getClientIp(HttpServletRequest request) {
        String remoteAddress = "";
        if (request != null) {
            remoteAddress = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddress == null || "".equals(remoteAddress)) {
                remoteAddress = request.getRemoteAddr();
            }
        }
        return remoteAddress;
    }

    @Around("execution(public * com.smikevon.easy.web.controller..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.nanoTime();

        Object res = null;
        String requestLogId = String.valueOf(IdWorker.nextId());
        ThreadContext.put(REQUEST_LOG_KEY, requestLogId);
        MDC.put(REQUEST_LOG_KEY, requestLogId);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String appVersion = StringUtils.trimToEmpty(request.getParameter("v"));
        String osVersion = StringUtils.trimToEmpty(request.getParameter("osv"));
        String phoneInfo = StringUtils.trimToEmpty(request.getParameter("pinfo"));
        String from = StringUtils.trimToEmpty(request.getParameter("f"));
        String requestTime = StringUtils.trimToEmpty(request.getParameter("t"));
        String uid = StringUtils.trimToEmpty(request.getParameter("uid"));

        Integer resultCode = null;
        String errorMsg = null;

        /**
         * 组装参数
         */
        Object[] args = pjp.getArgs();
        String[] paramNames = ((MethodSignature) pjp.getSignature()).getParameterNames();

        StringBuilder sb = new StringBuilder("{");
        for (int i = NumberUtils.INTEGER_ZERO; i < paramNames.length; i++) {
            if (args[i] instanceof BeanPropertyBindingResult || args[i] instanceof HttpServletRequest ||
                    args[i] instanceof HttpServletResponse) {
                continue;
            }

            sb.append(paramNames[i]).append("=");
            if (args[i] instanceof MultipartFile) {
                sb.append("文件内容");
            } else {
                sb.append(args[i]);
            }
            sb.append(i == paramNames.length - 1 ? "}" : ", ");
        }

        try {
            res = pjp.proceed();
            if (res instanceof Result) {
                Result result = (Result) res;
                result.setReqId(requestLogId);
                return result;
            }
            return res;
        } catch (BindException e) {
            List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
            FieldError firstError = fieldErrors.get(NumberUtils.INTEGER_ZERO);
            errorMsg = String.join(Delimiters.COLON, firstError.getField(), firstError.getDefaultMessage());
            resultCode = NumberUtils.INTEGER_MINUS_ONE;
            throw e;
        } catch (SQLException e) {
            errorMsg = "后台数据库错误";
            resultCode = NumberUtils.INTEGER_MINUS_ONE;
            throw e;
        } catch (Throwable e) {
            errorMsg = e.getMessage();
            resultCode = NumberUtils.INTEGER_MINUS_ONE;
            throw e;
        } finally {
            try {
                if (res != null && StringUtils.isBlank(errorMsg)) {
                    if (res instanceof Result) {
                        resultCode = ((Result) res).getCode();
                    } else {
                        resultCode = 200;
                        res = StringUtils.EMPTY;
                    }
                } else {
                    resultCode = resultCode == null ? 500 : resultCode;
                    res = errorMsg;
                }

                log(getClientIp(request),
                        request.getRequestURI(),
                        pjp.getSignature().getDeclaringType().getSimpleName(),
                        pjp.getSignature().getName(),
                        uid,
                        phoneInfo,
                        appVersion,
                        osVersion,
                        StringUtils.isBlank(requestTime) ? null : new Date(Long.valueOf(requestTime)),
                        from,
                        sb.toString(),
                        res,
                        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start),
                        resultCode);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            ThreadContext.clearAll();
            MDC.clear();
        }
    }

    /**
     * 记录调用日志
     */
    private void log(String ipAddress, String uri, String clazz, String method, String userId, String phoneInfo,
                     String appVersion, String osVersion, Date requestTime, String from, Object req, Object res,
                     Long cost, Integer status) {
        try {
            String requestTimeInfo = requestTime == null ? StringUtils.EMPTY : DateFormatUtils.format(requestTime,
                    Constant.DATE_TIME_FORMAT);
            String logMsg = "[IP=" + ipAddress + "] " +
                    "[URI=" + uri + "] " +
                    "[METHOD=" + StringUtils.join(ArrayUtils.toArray(clazz, method), ".") + "] " +
                    "[USER=" + userId + "] " +
                    "[REQUEST_TIME=" + requestTimeInfo + "] " +
                    "[PHONE_INFO=" + phoneInfo + "] " +
                    "[OS_VERSION=" + osVersion + "] " +
                    "[APP_VERSION=" + appVersion + "] " +
                    "[FROM=" + from + "] " +
                    "[REQUEST=" + mapper.writeValueAsString(req) + "] " +
                    "[RESPONSE=" + mapper.writeValueAsString(res) + "] " +
                    "[COST=" + cost + "] " +
                    "[RESULT_CODE=" + status + "] ";
            log.info(logMsg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
