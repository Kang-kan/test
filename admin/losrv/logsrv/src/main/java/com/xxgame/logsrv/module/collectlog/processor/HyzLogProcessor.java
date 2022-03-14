package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.HyzLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.HyzLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 混元珠日志处理
 */
@Service
public class HyzLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private HyzLogRepository hyzLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_HYZ;
    }

    @Override
    public void save(JSONObject jsonObject) {
        HyzLog log = new HyzLog();
        this.setBasePlayerInfo(log, jsonObject);

        log.setOpValue(jsonObject.getIntValue("opValue"));
        log.setKillCount(jsonObject.getIntValue("killCount"));
        log.setReward(jsonObject.getString("reward"));

        hyzLogRepository.save(log);
    }
}
