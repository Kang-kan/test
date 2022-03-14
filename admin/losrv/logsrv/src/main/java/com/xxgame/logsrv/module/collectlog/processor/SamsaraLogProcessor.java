package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.SamsaraExpLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.SamsaraExpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 轮回经验变化日志处理
 */
@Service
public class SamsaraLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private SamsaraExpRepository samsaraExpRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_SAMSARA;
    }

    @Override
    public void save(JSONObject jsonObject) {
        SamsaraExpLog samsaraExpLog = new SamsaraExpLog();
        this.setBasePlayerInfo(samsaraExpLog, jsonObject);

        samsaraExpLog.setValue(jsonObject.getLongValue("value"));
        samsaraExpLog.setChangeNum(jsonObject.getLongValue("changeNum"));
        samsaraExpLog.setFunctionType(jsonObject.getString("functionType"));
        samsaraExpLog.setSamsaraLevel(jsonObject.getIntValue("samsaraLevel"));

        samsaraExpRepository.save(samsaraExpLog);
    }
}
