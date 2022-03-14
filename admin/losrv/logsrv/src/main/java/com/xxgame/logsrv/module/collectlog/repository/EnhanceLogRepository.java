package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.EnhanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 强化日志
 * @author gil
 *
 */
public interface EnhanceLogRepository extends JpaRepository<EnhanceLog, Long> {

}
