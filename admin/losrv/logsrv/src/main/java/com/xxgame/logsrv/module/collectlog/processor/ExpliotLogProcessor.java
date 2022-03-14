package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ExpliotLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ExpliotLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 游戏币变化日志处理
 */
@Service
public class ExpliotLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ExpliotLogRepository expliotLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_EXPLIOT;
    }

    @Override
    public void save(JSONObject jsonObject) {
        ExpliotLog expliotLog = new ExpliotLog();
        this.setBasePlayerInfo(expliotLog, jsonObject);

        expliotLog.setValue(jsonObject.getLongValue("value"));
        expliotLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        expliotLog.setFunctionType(jsonObject.getString("functionType"));

        expliotLogRepository.save(expliotLog);
    }
}
