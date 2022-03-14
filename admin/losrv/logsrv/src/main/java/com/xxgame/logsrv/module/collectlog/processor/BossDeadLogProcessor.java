package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.BossDeadLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.BossDeadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * boss击杀日志处理
 */
@Service
public class BossDeadLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private BossDeadLogRepository bossDeadLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_BOSS_DEAD;
    }

    @Override
    public void save(JSONObject jsonObject) {
        BossDeadLog log = new BossDeadLog();

        log.setId(jsonObject.getLongValue("id"));
        log.setPlayType(jsonObject.getIntValue("playType"));
        log.setBattleId(jsonObject.getLongValue("battleId"));
        log.setBattleServer(jsonObject.getIntValue("battleServer"));
        log.setBossId(jsonObject.getIntValue("bossId"));
        log.setBossName(jsonObject.getString("bossName"));
        log.setOwnerType(jsonObject.getIntValue("ownerType"));
        log.setOwnerId(jsonObject.getLongValue("ownerId"));
        log.setOwnerName(jsonObject.getString("ownerName"));
        log.setRewardType(jsonObject.getIntValue("rewardType"));
        log.setReward(jsonObject.getString("reward"));
        log.setTime(jsonObject.getLongValue("time"));

        bossDeadLogRepository.save(log);
    }
}
