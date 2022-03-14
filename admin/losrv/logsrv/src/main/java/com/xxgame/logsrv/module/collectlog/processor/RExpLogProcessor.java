package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.RExpLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.RExpLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 转生修改变化日志处理
 */
@Service
public class RExpLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private RExpLogRepository rexpLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_REXP;
    }

    @Override
    public void save(JSONObject jsonObject) {
        RExpLog moneyLog = new RExpLog();
        this.setBasePlayerInfo(moneyLog, jsonObject);

        moneyLog.setValue(jsonObject.getLongValue("value"));
        moneyLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        moneyLog.setFunctionType(jsonObject.getString("functionType"));

        rexpLogRepository.save(moneyLog);
    }
}
