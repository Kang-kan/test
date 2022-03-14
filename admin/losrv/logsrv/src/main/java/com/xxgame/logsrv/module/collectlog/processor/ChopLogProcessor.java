package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ChopLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ChopLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 神器日志处理
 */
@Service
public class ChopLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ChopLogRepository chopLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_CHOP;
    }

    @Override
    public void save(JSONObject jsonObject) {
        ChopLog log = new ChopLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setChopId(jsonObject.getIntValue("chopId"));
        log.setChildId(jsonObject.getIntValue("childId"));

        chopLogRepository.save(log);
    }
}
