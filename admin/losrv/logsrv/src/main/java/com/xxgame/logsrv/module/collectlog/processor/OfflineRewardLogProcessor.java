package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.FashionLog;
import com.xxgame.logsrv.module.collectlog.entity.OfflineRewardLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.FashionLogRepository;
import com.xxgame.logsrv.module.collectlog.repository.OfflineRewardLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 离线挂机日志处理
 */
@Service
public class OfflineRewardLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private OfflineRewardLogRepository offlineRewardLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_OFFLINE_REWARD;
    }

    @Override
    public void save(JSONObject jsonObject) {
        OfflineRewardLog fashionLog = new OfflineRewardLog();
        this.setBasePlayerInfo(fashionLog, jsonObject);

        fashionLog.setGap(jsonObject.getLongValue("gap"));
        fashionLog.setBonus(jsonObject.getString("bonus"));

        offlineRewardLogRepository.save(fashionLog);
    }
}
