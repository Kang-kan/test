package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.OtherResourceLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.OtherResourceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 其它货币日志处理
 */
@Service
public class OtherResourceLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private OtherResourceLogRepository otherResourceLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_OTHER_RESOURCE;
    }

    @Override
    public void save(JSONObject jsonObject) {
        OtherResourceLog log = new OtherResourceLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setType(jsonObject.getIntValue("type"));
        log.setValue(jsonObject.getLongValue("value"));
        log.setChangeNum(jsonObject.getLongValue("changeNum"));
        log.setFunctionType(jsonObject.getString("functionType"));

        otherResourceLogRepository.save(log);
    }
}
