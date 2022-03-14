package com.xxgame.admin.web.modules.commons.cache;

/**
 * 本地缓存
 * @author gil
 *
 */
public interface LocalCacheService {

    /**
     * 从本地缓存中获取
     * @param key
     * @return
     */
    <T> T getValue(String key);

    /**
     * value 保存到本地缓存中
     * @param key
     * @param value
     */
    <T> void setValue(String key, T value);

    /**
     * 删除
     * @param key
     */
    void remove(String key);
}
