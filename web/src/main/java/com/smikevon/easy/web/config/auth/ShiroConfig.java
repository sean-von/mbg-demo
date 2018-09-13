package com.smikevon.easy.web.config.auth;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.pac4j.core.config.Config;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;

/**
 * Created by sean (smikevon@163.com) on 2018/9/13.
 */
@Configuration
public class ShiroConfig {

    /**
     * 项目工程路径
     */
    @Value("${cas.project.url}")
    private String projectUrl;

    /**
     * 项目cas服务路径
     */
    @Value("${cas.server.url}")
    private String casServerUrl;

    /**
     * 客户端名称
     */
    @Value("${cas.project.name}")
    private String clientName;

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(Pac4jSubjectFactory subjectFactory, SessionManager sessionManager,
                                                     CustomCasRealm casRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(casRealm);
        manager.setSubjectFactory(subjectFactory);
        manager.setSessionManager(sessionManager);
        return manager;
    }

    @Bean
    public CustomCasRealm customCasRealm() {
        CustomCasRealm realm = new CustomCasRealm();
        // 使用自定义的realm
        realm.setName("myPac4jRealm");
        realm.setCachingEnabled(false);
        //暂时不使用缓存
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthorizationCachingEnabled(false);
        //realm.setAuthenticationCacheName("authenticationCache");
        //realm.setAuthorizationCacheName("authorizationCache");
        return realm;
    }

    @Bean
    public Pac4jSubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        return filterRegistration;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @param shiroFilterFactoryBean
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        /*下面这些规则配置最好配置到配置文件中 */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index", "securityFilter");
        filterChainDefinitionMap.put("/callback", "callbackFilter");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "securityFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * shiroFilter
     *
     * @param securityManager
     * @param config
     *
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager, Config config) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 添加casFilter到shiroFilter中
        loadShiroFilterChain(shiroFilterFactoryBean);
        Map<String, Filter> filters = new HashMap<>(3);
        //cas 资源认证拦截器
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setConfig(config);
        securityFilter.setClients(clientName);
        filters.put("securityFilter", securityFilter);

        //cas 认证后回调拦截器
        CallbackFilter callbackFilter = new CustomCallBackFilter();
        callbackFilter.setConfig(config);
        callbackFilter.setDefaultUrl(projectUrl);
        callbackFilter.setMultiProfile(true);
        callbackFilter.setDefaultUrl("/");
        filters.put("callbackFilter", callbackFilter);

        // 注销 拦截器
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(config);
        logoutFilter.setCentralLogout(true);
        logoutFilter.setLocalLogout(true);
        logoutFilter.setDefaultUrl(projectUrl + "/callback?client_name=" + clientName);
        filters.put("logout", logoutFilter);
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new MemorySessionDAO();
    }

    /**
     * 自定义cookie名称
     *
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(SimpleCookie sessionIdCookie, SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(sessionIdCookie);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setGlobalSessionTimeout(TimeUnit.MINUTES.toMillis(30));
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
