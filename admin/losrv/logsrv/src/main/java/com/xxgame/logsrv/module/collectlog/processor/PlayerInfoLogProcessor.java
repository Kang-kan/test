package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.PlayerInfoLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.PlayerInfoLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家信息日志处理
 */
@Service
public class PlayerInfoLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private PlayerInfoLogRepository playerInfoLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_PLAYER_INFO;
    }

    @Override
    public void save(JSONObject jsonObject) {
        PlayerInfoLog playerInfoLog = new PlayerInfoLog();
        this.setBasePlayerInfo(playerInfoLog, jsonObject);

        playerInfoLog.setMoney(jsonObject.getLongValue("money"));
        playerInfoLog.setGold(jsonObject.getLongValue("gold"));
        playerInfoLog.setExp(jsonObject.getLongValue("exp"));
        playerInfoLog.setPower(jsonObject.getLongValue("power"));
        playerInfoLog.setVipLevel(jsonObject.getIntValue("vipLevel"));
        playerInfoLog.setVipExp(jsonObject.getIntValue("vipExp"));
        playerInfoLog.setrExp(jsonObject.getIntValue("rExp"));
        playerInfoLog.setrLevel(jsonObject.getIntValue("rLevel"));

        playerInfoLogRepository.save(playerInfoLog);
    }
}
