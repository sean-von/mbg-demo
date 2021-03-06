package com.smikevon.easy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@MapperScan("com.smikevon.easy.mbg.mapper")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.smikevon.easy")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
