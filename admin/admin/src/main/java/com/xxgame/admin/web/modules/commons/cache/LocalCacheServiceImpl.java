package com.xxgame.admin.web.modules.commons.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 * @author gil
 *
 */
public class LocalCacheServiceImpl implements LocalCacheService {

    /**
     *
     */
    private Cache<String, Object> cache;

    /**
     * 最大容量，默认80000
     * 缓存过期时间，默认5分钟
     */
    public LocalCacheServiceImpl() {
        this(80000, 5);
    }

    /**
     * @param cacheCapacity 最大容量，默认80000
     * @param duration      缓存过期时间，默认5分钟
     */
    public LocalCacheServiceImpl(int cacheCapacity, int duration) {
        cache = CacheBuilder.newBuilder()
                .maximumSize(cacheCapacity)
                .expireAfterAccess(duration, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public <T> T getValue(String key) {
        return (T) cache.getIfPresent(key);
    }

    @Override
    public <T> void setValue(String key, T value) {
        cache.put(key, value);
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }

}