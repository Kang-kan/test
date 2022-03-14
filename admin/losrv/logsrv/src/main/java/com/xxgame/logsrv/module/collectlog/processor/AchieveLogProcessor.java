package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.AchieveLog;
import com.xxgame.logsrv.module.collectlog.entity.FashionLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.AchieveLogRepository;
import com.xxgame.logsrv.module.collectlog.repository.FashionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 成就日志处理
 */
@Service
public class AchieveLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private AchieveLogRepository achieveLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_ACHIEVE;
    }

    @Override
    public void save(JSONObject jsonObject) {
        AchieveLog log = new AchieveLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setAchieveId(jsonObject.getIntValue("achieveId"));
        log.setBonus(jsonObject.getString("bonus"));

        achieveLogRepository.save(log);
    }
}
