package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.JianLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.JianLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 飞剑日志处理
 */
@Service
public class JianLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private JianLogRepository jianLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_JIAN;
    }

    @Override
    public void save(JSONObject jsonObject) {
        JianLog jianLog = new JianLog();
        this.setBasePlayerInfo(jianLog, jsonObject);

        jianLog.setHeroIndex(jsonObject.getIntValue("heroIndex"));
        jianLog.setJianlevel(jsonObject.getIntValue("jianlevel"));
        jianLog.setJianstar(jsonObject.getIntValue("jianstar"));
        jianLog.setFunctionType(jsonObject.getString("functionType"));

        jianLogRepository.save(jianLog);
    }
}
