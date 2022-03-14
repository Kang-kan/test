package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.PlayerInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 玩家信息日志
 * @author gil
 *
 */
public interface PlayerInfoLogRepository extends JpaRepository<PlayerInfoLog, Long> {

}
