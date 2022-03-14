package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.ChargeOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 充值定单日志
 * @author gil
 *
 */
public interface ChargeOrderLogRepository extends JpaRepository<ChargeOrderLog, Long> {

    /**
     * 根据定单号查找
     * @param orderId
     * @return
     */
    ChargeOrderLog findByOrderId(long orderId);

}
