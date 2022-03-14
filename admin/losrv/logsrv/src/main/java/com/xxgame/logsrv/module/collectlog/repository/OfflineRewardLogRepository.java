package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.OfflineRewardLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 离线挂机日志
 * @author gil
 *
 */
public interface OfflineRewardLogRepository extends JpaRepository<OfflineRewardLog, Long> {

}
