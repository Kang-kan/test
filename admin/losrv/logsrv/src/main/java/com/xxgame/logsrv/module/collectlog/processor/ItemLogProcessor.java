package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ItemLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ItemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 道具日志处理
 */
@Service
public class ItemLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ItemLogRepository itemLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_ITEM;
    }

    @Override
    public void save(JSONObject jsonObject) {
        ItemLog itemLog = new ItemLog();
        this.setBasePlayerInfo(itemLog, jsonObject);

        itemLog.setBonusType(jsonObject.getString("bonusType"));
        itemLog.setItemId(jsonObject.getIntValue("itemId"));
        itemLog.setItemName(jsonObject.getString("itemName"));
        itemLog.setCount(jsonObject.getIntValue("count"));
        itemLog.setCurrentCount(jsonObject.getIntValue("currentCount"));
        itemLog.setFunctionType(jsonObject.getString("functionType"));

        itemLogRepository.save(itemLog);
    }
}
