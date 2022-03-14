package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.ChargeOrderLog;
import com.xxgame.logsrv.module.collectlog.entity.QiLog;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ChargeOrderLogRepository;
import com.xxgame.logsrv.module.collectlog.repository.QiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 充值定单日志处理
 */
@Service
public class ChargeOrderLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ChargeOrderLogRepository chargeOrderLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_ORDER;
    }

    @Override
    public void save(JSONObject jsonObject) {
        long id = this.getId(jsonObject);
        Optional<ChargeOrderLog> optional = chargeOrderLogRepository.findById(id);
        if (optional.isPresent()) {
            return;
        }

        ChargeOrderLog chargeOrderLog = new ChargeOrderLog();
        chargeOrderLog.setId(id);
        chargeOrderLog.setAccount(jsonObject.getString("account"));
        chargeOrderLog.setPlayerId(jsonObject.getLongValue("playerId"));
        chargeOrderLog.setPlayerName(jsonObject.getString("playerName"));
        chargeOrderLog.setServerId(jsonObject.getIntValue("serverId"));
        chargeOrderLog.setChannelId(this.getChannelId(jsonObject));
        chargeOrderLog.setLevel(jsonObject.getIntValue("level"));
        chargeOrderLog.setPlatName(this.getPlatName(jsonObject));
        chargeOrderLog.setTime(jsonObject.getLongValue("time"));
        chargeOrderLog.setOrderId(jsonObject.getLongValue("orderId"));
        chargeOrderLog.setRmb(jsonObject.getIntValue("rmb"));
        chargeOrderLog.setGold(jsonObject.getIntValue("gold"));
        chargeOrderLog.setGoodsId(jsonObject.getIntValue("goodsId"));
        if (jsonObject.getBooleanValue("first")) {
            chargeOrderLog.setFirstRmb(chargeOrderLog.getRmb());
        }
        chargeOrderLog.setCreateDay(jsonObject.getBooleanValue("createDay"));

        chargeOrderLogRepository.save(chargeOrderLog);
    }
}
