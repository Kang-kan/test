package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.SealStoneLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.SealStoneLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 符文日志处理
 */
@Service
public class SealStoneLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private SealStoneLogRepository sealStoneLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_SEALSTONE;
    }

    @Override
    public void save(JSONObject jsonObject) {
        SealStoneLog sealStoneLog = new SealStoneLog();
        this.setBasePlayerInfo(sealStoneLog, jsonObject);

        sealStoneLog.setHeroConfigId(jsonObject.getIntValue("heroConfigId"));
        sealStoneLog.setOpValue(jsonObject.getIntValue("opValue"));
        sealStoneLog.setEquipIndex(jsonObject.getIntValue("equipIndex"));
        sealStoneLog.setEquipId(jsonObject.getIntValue("equipId"));

        sealStoneLogRepository.save(sealStoneLog);
    }
}
