package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.BasePlayerLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractCollectLogProcessor
 * @author gil
 */
public abstract class AbstractCollectLogProcessor implements CollectLogProcessor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取日志id
     * @param jsonObject
     * @return
     */
    public long getId(JSONObject jsonObject) {
        return jsonObject.getLongValue("id");
    }

    /**
     * 获取平台名
     * @param jsonObject
     * @return
     */
    public String getPlatName(JSONObject jsonObject) {
        String platName = jsonObject.getString("platName");
        return platName == null ? "" : platName;
    }

    /**
     * 获取渠道id
     * @param jsonObject
     * @return
     */
    public String getChannelId(JSONObject jsonObject) {
        String channelId = jsonObject.getString("channelId");
        return channelId == null ? "" : channelId;
    }

    /**
     * 获取lastIP
     * @param jsonObject
     * @return
     */
    public String getLastIp(JSONObject jsonObject) {
        String lastIp = jsonObject.getString("lastIP");
        return lastIp == null ? "" : lastIp;
    }

    /**
     * 设置基本玩家属性
     * @param basePlayerLog
     * @param jsonObject
     */
    public void setBasePlayerInfo(BasePlayerLogEntity basePlayerLog, JSONObject jsonObject) {
        basePlayerLog.setId(this.getId(jsonObject));
        basePlayerLog.setAccount(jsonObject.getString("account"));
        basePlayerLog.setPlayerId(jsonObject.getLongValue("playerId"));
        basePlayerLog.setPlayerName(jsonObject.getString("playerName"));
        basePlayerLog.setLevel(jsonObject.getIntValue("level"));
        basePlayerLog.setServerId(jsonObject.getIntValue("serverId"));
        basePlayerLog.setChannelId(this.getChannelId(jsonObject));
        basePlayerLog.setPlatName(this.getPlatName(jsonObject));
        basePlayerLog.setTime(jsonObject.getLongValue("time"));
    }

}
