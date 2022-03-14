package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.MeridianLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.MeridianLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 星宿日志处理
 */
@Service
public class MeridianLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private MeridianLogRepository meridianLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_MERIDIAN;
    }

    @Override
    public void save(JSONObject jsonObject) {
        MeridianLog log = new MeridianLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setMeridianLevel(jsonObject.getIntValue("meridianLevel"));
        log.setMeridianStep(jsonObject.getIntValue("meridianStep"));
        log.setCost(jsonObject.getString("cost"));

        meridianLogRepository.save(log);
    }
}
