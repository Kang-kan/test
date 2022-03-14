package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.TotemLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.TotemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仙兽蕴养日志处理
 */
@Service
public class TotemLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private TotemLogRepository totemLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_TOTEM;
    }

    @Override
    public void save(JSONObject jsonObject) {
        TotemLog log = new TotemLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setTotemId(jsonObject.getIntValue("totemId"));
        log.setTotemLevel(jsonObject.getIntValue("totemLevel"));
        log.setTotemChildLevel(jsonObject.getIntValue("totemChildLevel"));
        log.setExp(jsonObject.getIntValue("exp"));
        log.setCost(jsonObject.getString("cost"));

        totemLogRepository.save(log);
    }
}
