package com.xxgame.admin.web.modules.commons.service;

/**
 * 配置
 * @author gil
 */
public interface PropertyService {

    /**
     * 获取配置
     * @param propKey
     * @return
     */
    String getProperty(String propKey);

    /**
     * 获取配置，当key不存在的时候为0
     * @param propKey
     * @return
     */
    int getInt(String propKey);

}
