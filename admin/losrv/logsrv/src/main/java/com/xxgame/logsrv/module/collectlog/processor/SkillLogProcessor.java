package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.SkillLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.SkillLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 技能日志处理
 */
@Service
public class SkillLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private SkillLogRepository skillLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_SKILL;
    }

    @Override
    public void save(JSONObject jsonObject) {
        SkillLog log = new SkillLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setSkillIndex(jsonObject.getIntValue("skillIndex"));
        log.setSkillLevel(jsonObject.getIntValue("skillLevel"));
        log.setCost(jsonObject.getString("cost"));

        skillLogRepository.save(log);
    }
}
