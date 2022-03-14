package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.LogoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 管理后台登出日志
 * @author gil
 *
 */
public interface LogoutLogRepository extends JpaRepository<LogoutLog, Long> {

}
