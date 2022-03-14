package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.PlayerCreateLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.CreatePlayerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色创建日志处理
 */
@Service
public class PlayerCreateLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private CreatePlayerLogRepository createPlayerLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_CREATER_USER;
    }

    @Override
    public void save(JSONObject jsonObject) {
        PlayerCreateLog playerCreateLog = new PlayerCreateLog();
        this.setBasePlayerInfo(playerCreateLog, jsonObject);

        createPlayerLogRepository.save(playerCreateLog);
    }
}
