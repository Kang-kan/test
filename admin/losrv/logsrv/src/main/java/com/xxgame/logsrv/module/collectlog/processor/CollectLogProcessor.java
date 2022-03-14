package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.model.LogType;

import java.io.IOException;

/**
 * 日志收集处理器
 */
public interface CollectLogProcessor {

    /**
     * 日志类型
     * @return
     */
    LogType logType();

    /**
     * 保存日志
     * @param jsonObject
     */
    void save(JSONObject jsonObject) throws IOException;

}
