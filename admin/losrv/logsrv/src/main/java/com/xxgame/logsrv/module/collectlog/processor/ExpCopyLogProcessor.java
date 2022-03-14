package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ExpCopyLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ExpCopyLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 经验副本领取日志处理
 */
@Service
public class ExpCopyLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ExpCopyLogRepository expCopyLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_EXP_COPY;
    }

    @Override
    public void save(JSONObject jsonObject) {
        ExpCopyLog log = new ExpCopyLog();
        log.setId(this.getId(jsonObject));
        log.setAccount(jsonObject.getString("account"));
        log.setPlayerId(jsonObject.getLongValue("playerId"));
        log.setServerId(jsonObject.getIntValue("serverId"));
        log.setChannelId(this.getChannelId(jsonObject));
        log.setMultiple(jsonObject.getIntValue("multiple"));
        log.setTime(jsonObject.getLongValue("time"));

        expCopyLogRepository.save(log);
    }
}
