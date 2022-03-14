package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.QiLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.QiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 真气变化日志处理
 */
@Service
public class QiLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private QiLogRepository qiLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_QI;
    }

    @Override
    public void save(JSONObject jsonObject) {
        QiLog qiLog = new QiLog();
        this.setBasePlayerInfo(qiLog, jsonObject);

        qiLog.setValue(jsonObject.getLongValue("value"));
        qiLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        qiLog.setFunctionType(jsonObject.getString("functionType"));

        qiLogRepository.save(qiLog);
    }
}
