package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.CopyLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.CopyLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 副本日志处理
 */
@Service
public class CopyLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private CopyLogRepository copyLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_COPY;
    }

    @Override
    public void save(JSONObject jsonObject) {
        CopyLog copyLog = new CopyLog();
        this.setBasePlayerInfo(copyLog, jsonObject);

        copyLog.setOpValue(jsonObject.getByteValue("opValue"));
        copyLog.setCopyType(jsonObject.getIntValue("copyType"));
        copyLog.setCopyId(jsonObject.getIntValue("copyId"));
        copyLog.setOpValue(jsonObject.getIntValue("opValue"));
        copyLog.setBonus(jsonObject.getString("bonus"));
        copyLog.setExt(jsonObject.getString("ext"));

        copyLogRepository.save(copyLog);
    }
}
