package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.PlayerCreateLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 玩家创建日志
 * @author gil
 *
 */
public interface CreatePlayerLogRepository extends JpaRepository<PlayerCreateLog, Long> {

}
