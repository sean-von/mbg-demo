package com.smikevon.easy.web.config.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import io.buji.pac4j.filter.CallbackFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sean (smikevon@163.com) on 2018/9/13.
 */
@Slf4j
public class CustomCallBackFilter extends CallbackFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("after login, cas server invoke call back");
        super.doFilter(servletRequest, servletResponse, filterChain);
    }

}
