package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.TotemExpandLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.TotemExpandLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仙兽灵感日志处理
 */
@Service
public class TotemExpandLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private TotemExpandLogRepository totemExpandLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_TOTEM_EXPAND;
    }

    @Override
    public void save(JSONObject jsonObject) {
        TotemExpandLog log = new TotemExpandLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setExpandId(jsonObject.getIntValue("expandId"));
        log.setExpandLevel(jsonObject.getIntValue("expandLevel"));
        log.setSkillIndex(jsonObject.getIntValue("skillIndex"));
        log.setExpandBase(jsonObject.getIntValue("expandBase"));
        log.setCost(jsonObject.getString("cost"));

        totemExpandLogRepository.save(log);
    }
}
