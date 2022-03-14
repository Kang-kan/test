package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.GoldLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.GoldLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 金币日志处理
 */
@Service
public class GoldLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private GoldLogRepository goldLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_GOLD;
    }

    @Override
    public void save(JSONObject jsonObject) {
        GoldLog goldLog = new GoldLog();
        this.setBasePlayerInfo(goldLog, jsonObject);

        goldLog.setValue(jsonObject.getLongValue("value"));
        goldLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        goldLog.setFunctionType(jsonObject.getString("functionType"));

        goldLogRepository.save(goldLog);
    }
}
