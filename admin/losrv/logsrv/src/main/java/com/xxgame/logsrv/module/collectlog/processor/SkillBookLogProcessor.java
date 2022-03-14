package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.SkillBookLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.SkillBookLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 技能心法日志处理
 */
@Service
public class SkillBookLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private SkillBookLogRepository skillBookLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_SKILL_BOOK;
    }

    @Override
    public void save(JSONObject jsonObject) {
        SkillBookLog log = new SkillBookLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setSkillIndex(jsonObject.getIntValue("skillIndex"));
        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setSkillId(jsonObject.getIntValue("skillId"));
        log.setSkillLevel(jsonObject.getIntValue("skillLevel"));
        log.setCost(jsonObject.getString("cost"));
        log.setReward(jsonObject.getString("reward"));

        skillBookLogRepository.save(log);
    }
}
