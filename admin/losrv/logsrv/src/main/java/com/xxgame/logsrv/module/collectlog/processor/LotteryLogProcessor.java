package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.LotteryLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.LotteryLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 寻宝日志处理
 */
@Service
public class LotteryLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private LotteryLogRepository lotteryLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_LOTTERY;
    }

    @Override
    public void save(JSONObject jsonObject) {
        LotteryLog wingLog = new LotteryLog();
        wingLog.setId(this.getId(jsonObject));
        wingLog.setPlayerId(jsonObject.getLongValue("playerId"));
        wingLog.setPlayerName(jsonObject.getString("playerName"));
        wingLog.setEquipId(jsonObject.getIntValue("equipId"));
        wingLog.setFunctionType(jsonObject.getString("functionType"));
        wingLog.setTime(jsonObject.getLongValue("time"));

        lotteryLogRepository.save(wingLog);
    }
}
