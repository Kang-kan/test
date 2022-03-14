package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.FashionLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.FashionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 时装日志处理
 */
@Service
public class FashionLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private FashionLogRepository fashionLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_FASHION;
    }

    @Override
    public void save(JSONObject jsonObject) {
        FashionLog fashionLog = new FashionLog();
        this.setBasePlayerInfo(fashionLog, jsonObject);

        fashionLog.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        fashionLog.setOpValue(jsonObject.getByteValue("opValue"));
        fashionLog.setFashionId(jsonObject.getIntValue("fashionId"));
        fashionLog.setFashionLevel(jsonObject.getIntValue("fashionLevel"));
        fashionLog.setOverTime(jsonObject.getLongValue("overTime"));
        fashionLog.setWeared(jsonObject.getByteValue("weared"));

        fashionLogRepository.save(fashionLog);
    }
}
