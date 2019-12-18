package com.czk.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

/**
 * @Author: Created by ChenZK
 * @Create: 2019/12/17 16:53
 */

public class RedisCacheManager implements CacheManager {

    @Resource
    private RedisChache redisChache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisChache;
    }
}
