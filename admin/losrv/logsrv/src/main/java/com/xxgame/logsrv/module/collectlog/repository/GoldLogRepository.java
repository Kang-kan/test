package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.GoldLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 金币日志
 * @author gil
 *
 */
public interface GoldLogRepository extends JpaRepository<GoldLog, Long> {

}
