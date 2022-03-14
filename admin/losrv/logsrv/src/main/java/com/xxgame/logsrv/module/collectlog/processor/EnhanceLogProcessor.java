package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.EnhanceLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.EnhanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 强化日志处理
 */
@Service
public class EnhanceLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private EnhanceLogRepository enhanceLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_ENHANCE;
    }

    @Override
    public void save(JSONObject jsonObject) {
        EnhanceLog log = new EnhanceLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setType(jsonObject.getIntValue("type"));
        log.setEnIndex(jsonObject.getIntValue("enIndex"));
        log.setEnLevel(jsonObject.getIntValue("enLevel"));
        log.setCost(jsonObject.getString("cost"));

        enhanceLogRepository.save(log);
    }
}
