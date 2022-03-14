package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.PracticeLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.PracticeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 伙伴、圣魂、守护兽日志
 */
@Service
public class PracticeLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private PracticeLogRepository practiceLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_PRACTICE;
    }

    @Override
    public void save(JSONObject jsonObject) {
        PracticeLog log = new PracticeLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setConfigId(jsonObject.getIntValue("configId"));
        log.setChildConfigId(jsonObject.getIntValue("childConfigId"));
        log.setChildType(jsonObject.getIntValue("childType"));
        log.setCost(jsonObject.getString("cost"));
        log.setLevelId(jsonObject.getIntValue("levelId"));
        log.setExp(jsonObject.getIntValue("exp"));

        practiceLogRepository.save(log);
    }
}
