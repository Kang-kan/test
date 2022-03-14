package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.RebirthLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 转生日志
 * @author gil
 *
 */
public interface RebirthLogRepository extends JpaRepository<RebirthLog, Long> {

}
