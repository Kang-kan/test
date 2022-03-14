package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.SerialGiftLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.SerialGiftLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 礼包码日志处理
 */
@Service
public class SerialGiftLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private SerialGiftLogRepository serialGiftLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_SERIAL_GIFT;
    }

    @Override
    public void save(JSONObject jsonObject) {
        SerialGiftLog serialGiftLog = new SerialGiftLog();
        this.setBasePlayerInfo(serialGiftLog, jsonObject);

        serialGiftLog.setCdKey(jsonObject.getString("cdKey"));
        serialGiftLog.setBonus(jsonObject.getString("bonus"));

        serialGiftLogRepository.save(serialGiftLog);
    }
}
