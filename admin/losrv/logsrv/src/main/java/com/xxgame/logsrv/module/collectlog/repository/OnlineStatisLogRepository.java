package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.OnlineStatisLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 在线日志统计
 * @author gil
 *
 */
public interface OnlineStatisLogRepository extends JpaRepository<OnlineStatisLog, Long> {

}
