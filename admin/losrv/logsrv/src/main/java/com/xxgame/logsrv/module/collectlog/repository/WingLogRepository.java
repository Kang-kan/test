package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.WingLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 翅膀日志
 * @author gil
 *
 */
public interface WingLogRepository extends JpaRepository<WingLog, Long> {

}
