package com.xxgame.admin.web.modules.user.repository;

import com.xxgame.admin.web.modules.user.entity.SystemUserLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 管理后台登录日志
 * @author gil
 *
 */
public interface SystemUserLogRepository extends JpaRepository<SystemUserLog, Long> {
}
