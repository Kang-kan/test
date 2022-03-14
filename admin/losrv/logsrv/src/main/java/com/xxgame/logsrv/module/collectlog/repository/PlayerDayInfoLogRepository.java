package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.PlayerDayInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 玩家每日状态信息
 * @author gil
 *
 */
public interface PlayerDayInfoLogRepository extends JpaRepository<PlayerDayInfoLog, Long> {

}
