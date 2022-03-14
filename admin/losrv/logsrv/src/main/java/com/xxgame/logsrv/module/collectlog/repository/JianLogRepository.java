package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.JianLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 飞剑日志
 * @author gil
 *
 */
public interface JianLogRepository extends JpaRepository<JianLog, Long> {

}
