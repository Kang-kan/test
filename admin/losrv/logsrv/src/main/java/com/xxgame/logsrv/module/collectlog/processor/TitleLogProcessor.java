package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.TitleLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.TitleLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 称号日志处理
 */
@Service
public class TitleLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private TitleLogRepository titleLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_TITLE;
    }

    @Override
    public void save(JSONObject jsonObject) {
        TitleLog log = new TitleLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setTitleId(jsonObject.getIntValue("titleId"));
        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setExpireTime(jsonObject.getIntValue("expireTime"));

        titleLogRepository.save(log);
    }
}
