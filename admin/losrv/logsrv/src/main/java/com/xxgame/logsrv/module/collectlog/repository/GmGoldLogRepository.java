package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.GmGoldLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 内币日志
 * @author gil
 *
 */
public interface GmGoldLogRepository extends JpaRepository<GmGoldLog, Long> {

}
