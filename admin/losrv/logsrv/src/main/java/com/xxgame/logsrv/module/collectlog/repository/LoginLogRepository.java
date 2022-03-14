package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 管理后台登录日志
 * @author gil
 *
 */
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {

}
