package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ExpLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ExpLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 经验修改变化日志处理
 */
@Service
public class ExpLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ExpLogRepository expLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_EXP;
    }

    @Override
    public void save(JSONObject jsonObject) {
        ExpLog expLog = new ExpLog();
        this.setBasePlayerInfo(expLog, jsonObject);

        expLog.setValue(jsonObject.getLongValue("value"));
        expLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        expLog.setFunctionType(jsonObject.getString("functionType"));
        expLog.setExt(jsonObject.getString("ext"));

        expLogRepository.save(expLog);
    }
}
