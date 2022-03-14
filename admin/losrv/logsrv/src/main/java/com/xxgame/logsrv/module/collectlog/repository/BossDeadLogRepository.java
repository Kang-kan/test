package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.BossDeadLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * boss击杀日志
 * @author gil
 *
 */
public interface BossDeadLogRepository extends JpaRepository<BossDeadLog, Long> {

}
