package com.smikevon.easy.web.config.auth;

import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.buji.pac4j.context.ShiroSessionStore;

/**
 * Created by sean (smikevon@163.com) on 2018/9/13.
 */
@Configuration
public class Pac4jConfig {

    /*@Value("${cas.url.login}")
    private String casLoginUrl;

    @Value("${cas.url.callback}")
    private String casCallBackUrl;*/
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

    @Bean
    public CasConfiguration casConfiguration() {
        CasConfiguration configuration = new CasConfiguration();
        configuration.setLoginUrl(casServerUrl + "login");
        configuration.setPrefixUrl(casServerUrl);
        configuration.setAcceptAnyProxy(true);
        configuration.setProtocol(CasProtocol.CAS20);
        return configuration;
    }

    @Bean
    public CustomCasClient casClient(CasConfiguration casConfiguration) {
        CustomCasClient casClient = new CustomCasClient(casConfiguration);
        casClient.setName(clientName);
        casClient.setCallbackUrl(projectUrl + "/callback?client_name=" + clientName);
        return casClient;
    }

    @Bean
    public Clients clients() {
        return new Clients(projectUrl + "/callback?client_name=" + clientName, casClient(casConfiguration()));
    }

    /**
     * 自定义存储
     *
     * @return
     */
    @Bean
    public ShiroSessionStore shiroSessionStore() {
        return new ShiroSessionStore();
    }

    /**
     * pac4j配置
     *
     * @param casClient
     * @param shiroSessionStore
     *
     * @return
     */
    @Bean("authcConfig")
    public Config config(CustomCasClient casClient, ShiroSessionStore shiroSessionStore) {
        Config config = new Config(casClient);
        config.setSessionStore(shiroSessionStore);
        return config;
    }

}
