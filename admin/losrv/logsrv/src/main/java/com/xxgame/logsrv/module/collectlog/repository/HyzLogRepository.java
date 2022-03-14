package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.HyzLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 混元珠日志
 * @author gil
 *
 */
public interface HyzLogRepository extends JpaRepository<HyzLog, Long> {

}
