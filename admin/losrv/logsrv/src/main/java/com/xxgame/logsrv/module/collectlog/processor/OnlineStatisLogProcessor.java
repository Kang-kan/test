package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.OnlineStatisLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.OnlineStatisLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 在线日志统计
 */
@Service
public class OnlineStatisLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private OnlineStatisLogRepository onlineStatisLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_ONLINE_STATIS;
    }

    @Override
    public void save(JSONObject jsonObject) {
        OnlineStatisLog onlineStatisLog = new OnlineStatisLog();
        onlineStatisLog.setId(this.getId(jsonObject));
        onlineStatisLog.setTime(jsonObject.getLongValue("time"));
        onlineStatisLog.setServerId(jsonObject.getIntValue("serverId"));
        onlineStatisLog.setChannelId(this.getChannelId(jsonObject));
        onlineStatisLog.setCount(jsonObject.getIntValue("count"));

        onlineStatisLogRepository.save(onlineStatisLog);
    }
}
