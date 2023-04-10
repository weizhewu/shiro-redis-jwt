package com.waltz.springshirostudy.config;


import com.waltz.springshirostudy.constant.RedisConstant;
import com.waltz.springshirostudy.service.RedisService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: CustomCache
 * @createDate: 2023/4/7 13:04
 **/
public class CustomCache <K,V> implements Cache<K,V> {


    @Resource
    private RedisService redisService;
    /**
     * 缓存的key名称获取为shiro:cache:principalIdFieldName
     *
     * @param key key
     * @return java.lang.String
     */
    private String getKey(Object key) {
        return RedisConstant.PREFIX_SHIRO_CACHE + key.toString();
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        return  redisService.getObject(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public String put(Object key, Object value) throws CacheException {
        redisService.saveObject(this.getKey(key), value, RedisConstant.SHIRO_CACHE_EXPIRATION_TIME);
        return RedisConstant.OK;
    }

    /**
     * 移除缓存
     */
    @Override
    public V remove(Object key) throws CacheException {
        redisService.removeKey(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        // JedisUtil.getJedis().flushDB();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        return null;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        return null;
    }
}