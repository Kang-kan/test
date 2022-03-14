package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.LoginLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录日志处理
 */
@Service
public class LoginLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_LOGIN;
    }

    @Override
    public void save(JSONObject jsonObject) {
        LoginLog loginLog = new LoginLog();
        this.setBasePlayerInfo(loginLog, jsonObject);

        loginLog.setLastIP(this.getLastIp(jsonObject));
        loginLog.setPower(jsonObject.getLongValue("power"));
        loginLog.setRegistDay(jsonObject.getBooleanValue("registDay"));
        loginLog.setSysLog(jsonObject.getBooleanValue("sysLog"));

        loginLogRepository.save(loginLog);
    }
}
