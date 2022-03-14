package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.FamilyLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.FamilyLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仙盟日志处理
 */
@Service
public class FamilyLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private FamilyLogRepository familyLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_FAMILY;
    }

    @Override
    public void save(JSONObject jsonObject) {
        FamilyLog familyLog = new FamilyLog();
        familyLog.setId(jsonObject.getLongValue("id"));
        familyLog.setFamilyId(jsonObject.getLongValue("familyId"));
        familyLog.setName(jsonObject.getString("name"));
        familyLog.setLeaderName(jsonObject.getString("leaderName"));
        familyLog.setExp(jsonObject.getIntValue("exp"));
        familyLog.setLevel(jsonObject.getIntValue("level"));
        familyLog.setOpValue(jsonObject.getIntValue("opValue"));
        familyLog.setOpPlayerId(jsonObject.getLongValue("opPlayerId"));
        familyLog.setOpPlayerName(jsonObject.getString("opPlayerName"));
        familyLog.setOpPos(jsonObject.getIntValue("opPos"));
        familyLog.setTargetPlayerId(jsonObject.getLongValue("targetPlayerId"));
        familyLog.setTargetPlayerName(jsonObject.getString("targetPlayerName"));
        familyLog.setTargetPos(jsonObject.getIntValue("targetPos"));
        familyLog.setTime(jsonObject.getLongValue("time"));

        familyLogRepository.save(familyLog);
    }
}
