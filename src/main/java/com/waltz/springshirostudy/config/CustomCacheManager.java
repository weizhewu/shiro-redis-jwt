package com.waltz.springshirostudy.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: CustomCacheManager
 * @createDate: 2023/4/7 13:04
 **/
public class CustomCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<>();
    }
}