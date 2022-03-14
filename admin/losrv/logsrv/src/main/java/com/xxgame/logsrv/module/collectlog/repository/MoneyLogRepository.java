package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.MoneyLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 游戏币变化日志
 * @author gil
 *
 */
public interface MoneyLogRepository extends JpaRepository<MoneyLog, Long> {

}
