package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.ExpliotLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 灵压变化日志
 * @author gil
 *
 */
public interface ExpliotLogRepository extends JpaRepository<ExpliotLog, Long> {

}
