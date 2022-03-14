package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.ExpLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 等级经验变化日志
 * @author gil
 *
 */
public interface ExpLogRepository extends JpaRepository<ExpLog, Long> {

}
