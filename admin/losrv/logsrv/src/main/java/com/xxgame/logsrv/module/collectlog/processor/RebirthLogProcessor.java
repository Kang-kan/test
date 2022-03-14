package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.RebirthLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.RebirthLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 转生日志处理
 */
@Service
public class RebirthLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private RebirthLogRepository rebirthLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_REBIRTH;
    }

    @Override
    public void save(JSONObject jsonObject) {
        RebirthLog rebirthLog = new RebirthLog();
        this.setBasePlayerInfo(rebirthLog, jsonObject);

        rebirthLog.setBeforeLevel(jsonObject.getIntValue("beforeLevel"));
        rebirthLog.setCost(jsonObject.getString("cost"));

        rebirthLogRepository.save(rebirthLog);
    }
}
