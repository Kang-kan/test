package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.MarketLog;
import com.xxgame.logsrv.module.collectlog.entity.WingLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商城日志
 * @author gil
 *
 */
public interface MarketLogRepository extends JpaRepository<MarketLog, Long> {

}
