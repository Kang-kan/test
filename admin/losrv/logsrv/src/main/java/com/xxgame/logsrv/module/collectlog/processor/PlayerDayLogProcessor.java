package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.PlayerDayInfoLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.PlayerDayInfoLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家每日状态信息处理
 */
@Service
public class PlayerDayLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private PlayerDayInfoLogRepository playerDayInfoLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_PLAYER_DAY_INFO;
    }

    @Override
    public void save(JSONObject jsonObject) {
        PlayerDayInfoLog playerDayInfoLog = new PlayerDayInfoLog();
        this.setBasePlayerInfo(playerDayInfoLog, jsonObject);

        playerDayInfoLog.setDayOnlineTime(jsonObject.getIntValue("dayOnlineTime"));
        playerDayInfoLog.setMoney(jsonObject.getLongValue("money"));
        playerDayInfoLog.setGold(jsonObject.getLongValue("gold"));
        playerDayInfoLog.setExp(jsonObject.getLongValue("exp"));
        playerDayInfoLog.setrExp(jsonObject.getIntValue("rExp"));

        playerDayInfoLogRepository.save(playerDayInfoLog);
    }
}
