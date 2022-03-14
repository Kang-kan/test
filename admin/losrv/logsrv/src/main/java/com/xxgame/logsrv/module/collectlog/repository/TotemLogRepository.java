package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.TotemLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 仙兽蕴养日志
 * @author gil
 *
 */
public interface TotemLogRepository extends JpaRepository<TotemLog, Long> {

}
