package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.ChopLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 神器日志
 * @author gil
 *
 */
public interface ChopLogRepository extends JpaRepository<ChopLog, Long> {

}
