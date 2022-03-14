package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.PlayerStatisLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.PlayerStatisLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 翅膀日志处理
 */
@Service
public class PlayerStatisLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private PlayerStatisLogRepository playerStatisLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_PLAYER_STATIS;
    }

    @Override
    public void save(JSONObject jsonObject) {
        PlayerStatisLog playerStatisLog = new PlayerStatisLog();
        playerStatisLog.setDaily(jsonObject.getIntValue("daily"));
        playerStatisLog.setServerId(jsonObject.getIntValue("serverId"));
        playerStatisLog.setTotal(jsonObject.getIntValue("total"));
        playerStatisLog.setLevelMap(jsonObject.getString("levelMap"));
        playerStatisLog.setId("" + playerStatisLog.getDaily() + playerStatisLog.getServerId());

        playerStatisLogRepository.save(playerStatisLog);
    }
}
