package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.MarketLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.MarketLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商城日志处理
 */
@Service
public class MarketLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private MarketLogRepository marketLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_MARKET;
    }

    @Override
    public void save(JSONObject jsonObject) {
        MarketLog marketLog = new MarketLog();
        marketLog.setId(this.getId(jsonObject));
        marketLog.setAccount(jsonObject.getString("account"));
        marketLog.setPlayerId(jsonObject.getLongValue("playerId"));
        marketLog.setServerId(jsonObject.getIntValue("serverId"));
        marketLog.setPlayerName(jsonObject.getString("playerName"));
        marketLog.setLevel(jsonObject.getIntValue("level"));
        marketLog.setItemId(jsonObject.getIntValue("itemId"));
        marketLog.setItemName(jsonObject.getString("itemName"));
        marketLog.setPrice(jsonObject.getIntValue("price"));
        marketLog.setCount(jsonObject.getIntValue("count"));
        marketLog.setCurrency(jsonObject.getIntValue("currency"));
        marketLog.setAmount(jsonObject.getIntValue("amount"));
        marketLog.setChannelId(this.getChannelId(jsonObject));
        marketLog.setTime(jsonObject.getLongValue("time"));

        marketLogRepository.save(marketLog);
    }
}
