package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.LotteryLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 寻宝日志
 * @author gil
 *
 */
public interface LotteryLogRepository extends JpaRepository<LotteryLog, Long> {

}
