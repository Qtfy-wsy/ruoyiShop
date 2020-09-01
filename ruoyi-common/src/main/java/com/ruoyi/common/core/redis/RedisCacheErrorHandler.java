package com.ruoyi.common.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Service;

/**
 * Created by 魔金商城 on 18/2/5.
 * redis 异常处理器
 */
@Service
public class RedisCacheErrorHandler implements CacheErrorHandler {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(RedisCacheErrorHandler.class);

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        logger.error("handleCacheClearError", e);
    }

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        logger.error("get from redis fail and cache:{} \r\n o:{}", cache, o);
        logger.error("handleCacheGetError", e);

    }


    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        logger.error("Evict from redis fail and cache:{} \r\n o:{}", cache, o);
        logger.error("handleCacheEvictError", e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        logger.error("handleCachePutError", e);
    }
}
