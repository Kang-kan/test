package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.CopyLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 副本日志
 * @author gil
 *
 */
public interface CopyLogRepository extends JpaRepository<CopyLog, Long> {

}
