package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.SealStoneLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 符文日志
 * @author gil
 *
 */
public interface SealStoneLogRepository extends JpaRepository<SealStoneLog, Long> {

}
