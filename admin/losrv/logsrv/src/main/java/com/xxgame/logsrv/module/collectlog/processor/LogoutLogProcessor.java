package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.LogoutLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.LogoutLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登出日志处理
 */
@Service
public class LogoutLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private LogoutLogRepository logoutLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_LOGOUT;
    }

    @Override
    public void save(JSONObject jsonObject) {
        LogoutLog logoutLog = new LogoutLog();
        this.setBasePlayerInfo(logoutLog, jsonObject);

        logoutLog.setLastIP(this.getLastIp(jsonObject));
        logoutLog.setPower(jsonObject.getLongValue("power"));
        logoutLog.setRegistDay(jsonObject.getBooleanValue("registDay"));
        logoutLog.setLoginTime(jsonObject.getLongValue("loginTime"));
        logoutLog.setSysLog(jsonObject.getBooleanValue("sysLog"));

        logoutLogRepository.save(logoutLog);
    }
}
