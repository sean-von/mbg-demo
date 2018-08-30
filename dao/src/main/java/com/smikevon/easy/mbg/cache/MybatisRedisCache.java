package com.smikevon.easy.mbg.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;

import com.smikevon.easy.mbg.config.RedisConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * 注意，ID的命名不要随便变更，会导致 mybatis 查找 cache 时，找不到 namespace 的错误。
 * 同一个 namespace 多个 xml 的情况下更是一场灾难
 * <p>
 * Created by sean (smikevon@163.com) on 2018/8/30.
 */
@Slf4j
public class MybatisRedisCache implements Cache {

    /**
     * 不要加东西，保持为空，多么痛苦的领悟。
     */
    private static final String MYBATIS_DB_CACHE_PREFIX = "";

    private final String id;
    private final ReadWriteLock readWriteLock;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = MYBATIS_DB_CACHE_PREFIX + id;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (key != null && value != null) {
            RedisTemplate<Object, Object> redisTemplate = RedisContextUtils.getRedisTemplate();
            if (redisTemplate != null) {
                try {
                    redisTemplate.opsForHash().put(id, key, value);
                    if (log.isDebugEnabled()) {
                        debug("put query result ({} on Key <KEY>\"{}\"</KEY>) to cache", id, key);
                    }
                } catch (RedisConnectionFailureException e) {
                    if (log.isWarnEnabled()) {
                        log.warn(e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public Object getObject(Object key) {
        if (key == null) {
            return null;
        }
        RedisTemplate<Object, Object> redisTemplate = RedisContextUtils.getRedisTemplate();
        if (redisTemplate != null) {
            try {
                Object value = redisTemplate.opsForHash().get(id, key);
                if (value != null) {
                    if (log.isDebugEnabled()) {
                        debug("get query result ({} on Key <KEY>\"{}\"</KEY>) from cache", id, key);
                    }
                    return value;
                }
            } catch (RedisConnectionFailureException e) {
                if (log.isWarnEnabled()) {
                    log.warn(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (key == null) {
            return null;
        }
        RedisTemplate<Object, Object> redisTemplate = RedisContextUtils.getRedisTemplate();
        if (redisTemplate == null) {
            return null;
        }
        try {
            Object obj = redisTemplate.opsForHash().delete(id, key);
            if (log.isDebugEnabled()) {
                debug("remove query result ({} on Key <KEY>\"{}\"</KEY>) from cache", id, key);
            }
            return obj;
        } catch (RedisConnectionFailureException e) {
            if (log.isWarnEnabled()) {
                log.warn(e.getMessage());
            }
            return null;
        }
    }

    @Override
    public void clear() {
        RedisTemplate<Object, Object> redisTemplate = RedisContextUtils.getRedisTemplate();
        if (redisTemplate != null) {
            if (log.isDebugEnabled()) {
                log.debug("clear query result ({}) from cache", id);
            }
            try {
                redisTemplate.delete(id);
            } catch (RedisConnectionFailureException e) {
                if (log.isWarnEnabled()) {
                    log.warn(e.getMessage());
                }
            }
        }
    }

    @Override
    public int getSize() {
        RedisTemplate<Object, Object> redisTemplate = RedisContextUtils.getRedisTemplate();
        if (redisTemplate == null) {
            return 0;
        }
        try {
            return redisTemplate.opsForHash().size(id).intValue();
        } catch (RedisConnectionFailureException e) {
            if (log.isWarnEnabled()) {
                log.warn(e.getMessage());
            }
            return 0;
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public String toString() {
        return "Redis {" + id + "}";
    }

    public void debug(String format, Object key, Object arg) {
        try {
            log.debug(format, key, StringEscapeUtils.escapeJava(RedisConfiguration.getObjectMapper()
                    .writeValueAsString(arg)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
