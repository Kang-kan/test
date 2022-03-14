package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.PlayerStatisLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 玩家信息统计
 * @author gil
 *
 */
public interface PlayerStatisLogRepository extends JpaRepository<PlayerStatisLog, String> {

}
