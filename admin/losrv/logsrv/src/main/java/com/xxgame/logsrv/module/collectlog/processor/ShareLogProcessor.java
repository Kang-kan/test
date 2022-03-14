package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ShareLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ShareLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分享日志处理
 */
@Service
public class ShareLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ShareLogRepository shareLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_SHARE;
    }

    @Override
    public void save(JSONObject jsonObject) {
        ShareLog shareLog = new ShareLog();
        this.setBasePlayerInfo(shareLog, jsonObject);

        shareLog.setOpValue(jsonObject.getByteValue("opValue"));
        shareLog.setShareCount(jsonObject.getIntValue("shareCount"));
        shareLog.setShareRewardCount(jsonObject.getIntValue("shareRewardCount"));

        shareLogRepository.save(shareLog);
    }
}
