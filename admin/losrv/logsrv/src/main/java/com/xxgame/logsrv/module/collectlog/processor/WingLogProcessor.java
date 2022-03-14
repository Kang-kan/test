package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.WingLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.WingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 翅膀日志处理
 */
@Service
public class WingLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private WingLogRepository wingLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_WING;
    }

    @Override
    public void save(JSONObject jsonObject) {
        WingLog wingLog = new WingLog();
        this.setBasePlayerInfo(wingLog, jsonObject);

        wingLog.setHeroIndex(jsonObject.getIntValue("heroIndex"));
        wingLog.setWinglevel(jsonObject.getIntValue("winglevel"));
        wingLog.setWingstar(jsonObject.getIntValue("wingstar"));
        wingLog.setFunctionType(jsonObject.getString("functionType"));

        wingLogRepository.save(wingLog);
    }
}
