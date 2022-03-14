package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.MoneyLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.MoneyLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 游戏币变化日志处理
 */
@Service
public class MoneyLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private MoneyLogRepository moneyLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_MONEY;
    }

    @Override
    public void save(JSONObject jsonObject) {
        MoneyLog moneyLog = new MoneyLog();
        this.setBasePlayerInfo(moneyLog, jsonObject);

        moneyLog.setValue(jsonObject.getLongValue("value"));
        moneyLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        moneyLog.setFunctionType(jsonObject.getString("functionType"));

        moneyLogRepository.save(moneyLog);
    }
}
