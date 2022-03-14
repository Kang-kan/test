package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.AchieveLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 成就日志
 * @author gil
 *
 */
public interface AchieveLogRepository extends JpaRepository<AchieveLog, Long> {

}
