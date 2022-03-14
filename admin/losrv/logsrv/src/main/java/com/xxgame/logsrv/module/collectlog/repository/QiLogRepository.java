package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.QiLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 真气变化日志
 * @author gil
 *
 */
public interface QiLogRepository extends JpaRepository<QiLog, Long> {

}
