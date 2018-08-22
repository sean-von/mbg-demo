package com.smikevon.easy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.smikevon.easy.web.config.CommonConfig;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@MapperScan("com.smikevon.easy.mbg.mapper")
@ComponentScan("com.smikevon.easy")
@EnableConfigurationProperties({CommonConfig.class})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
