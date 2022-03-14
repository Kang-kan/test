package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.exception.WaittingOtherLogException;
import com.xxgame.logsrv.module.collectlog.entity.ChargeOrderLog;
import com.xxgame.logsrv.module.collectlog.model.ChargerResultCode;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import com.xxgame.logsrv.module.collectlog.repository.ChargeOrderLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 充值服回调日志处理
 */
@Service
public class ChargePayLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private ChargeOrderLogRepository chargeOrderLogRepository;

    @Override
    public LogType logType() {
        return LogType.LOG_PAY;
    }

    @Override
    public void save(JSONObject jsonObject) {
        long id = this.getId(jsonObject);
        ChargeOrderLog chargeOrderLog = chargeOrderLogRepository.findByOrderId(id);
        if (chargeOrderLog == null) {
            throw new WaittingOtherLogException("找不到充值定单，请检查游戏服是否已接MQ，如果是未开服测试可忽略，定单id：" + id);
        }

        // 已经完成了
        if (StringUtils.equalsAny(chargeOrderLog.getGameResultCode(), ChargerResultCode.SUCCESS.getCode(), ChargerResultCode.FINISHED.getCode())) {
            return;
        }

        chargeOrderLog.setTransactionId(jsonObject.getString("transactionId"));
        chargeOrderLog.setArrivalTime(jsonObject.getLongValue("createTime")); // 到帐时间
        chargeOrderLog.setAdviceTime(jsonObject.getLongValue("finishTime")); // 发货完成时间
        chargeOrderLog.setGameResultCode(jsonObject.getString("gameResultCode"));

        chargeOrderLogRepository.save(chargeOrderLog);
    }
}
