package com.smikevon.easy.common.utils;

import lombok.extern.java.Log;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
@Log
public class IdWorker {

    private static Integer dataCenterId = null;
    private static Integer workerId = null;

    private static volatile Snowflake snowflake = null;

    private IdWorker() {
    }

    /**
     * 通过环境变量对基础配置进行初始化
     */
    public static void init(Integer dataCenterId, Integer workerId) {
        IdWorker.dataCenterId = dataCenterId;
        IdWorker.workerId = workerId;
        log.info(getSnowflake().toString());
    }

    public static Long nextId() {
        return getSnowflake().nextId();
    }

    private static Snowflake getSnowflake() {
        if (snowflake == null) {
            synchronized(Snowflake.class) {
                if (dataCenterId == null || workerId == null) {
                    throw new NullPointerException("SnowFlake worker init fail!");
                }
                snowflake = new Snowflake(workerId, dataCenterId);
            }
        }
        return snowflake;
    }

}
