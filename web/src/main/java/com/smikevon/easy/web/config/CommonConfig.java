package com.smikevon.easy.web.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.smikevon.easy.common.utils.Delimiters;
import com.smikevon.easy.common.utils.IPUtils;
import com.smikevon.easy.common.utils.IdWorker;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sean (smikevon@163.com) on 2018/8/22.
 */
@Slf4j
@ToString
@Configuration
@ConfigurationProperties(prefix = "common.config")
@PropertySource("classpath:common.yml")
public class CommonConfig {

    @Getter
    private String ipAddress;

    @Value("${dataCenters}")
    private String dataCenters;

    @Value("${workerIds}")
    private String workerIds;

    @Getter
    private Integer dataCenterId;
    @Getter
    private Integer workerId;

    @PostConstruct
    public void init() {
        try {
            ipAddress = IPUtils.getIp4Address();
            log.info("init common config is {}", toString());

            Arrays.stream(dataCenters.split(Delimiters.COMMA)).forEach(item -> {
                String[] snowFlake = item.split(Delimiters.COLON);
                if (snowFlake[NumberUtils.INTEGER_ZERO].equalsIgnoreCase(ipAddress)) {
                    this.dataCenterId = Integer.valueOf(snowFlake[NumberUtils.INTEGER_ONE]);
                }
            });

            Arrays.stream(workerIds.split(Delimiters.COMMA)).forEach(item -> {
                String[] snowFlake = item.split(Delimiters.COLON);
                if (snowFlake[NumberUtils.INTEGER_ZERO].equalsIgnoreCase(ipAddress)) {
                    this.workerId = Integer.valueOf(snowFlake[NumberUtils.INTEGER_ONE]);
                }
            });

            log.info("after common config is {}", toString());
            IdWorker.init(dataCenterId, workerId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
