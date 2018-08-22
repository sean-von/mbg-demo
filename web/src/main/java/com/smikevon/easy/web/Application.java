package com.smikevon.easy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.smikevon.easy.web.config.CommonConfig;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@MapperScan("com.smikevon.easy.mbg.mapper")
@EnableTransactionManagement
@EnableConfigurationProperties({CommonConfig.class})
@SpringBootApplication(scanBasePackages = "com.smikevon.easy")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
