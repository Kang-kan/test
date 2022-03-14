package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.GmGoldLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.GmGoldLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 内币日志处理
 */
@Service
public class GmGoldLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private GmGoldLogRepository gmGoldLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_GM_GOLD;
    }

    @Override
    public void save(JSONObject jsonObject) {
        GmGoldLog gmGoldLog = new GmGoldLog();
        this.setBasePlayerInfo(gmGoldLog, jsonObject);

        gmGoldLog.setValue(jsonObject.getLongValue("value"));
        gmGoldLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        gmGoldLog.setFunctionType(jsonObject.getString("functionType"));

        gmGoldLogRepository.save(gmGoldLog);
    }
}
