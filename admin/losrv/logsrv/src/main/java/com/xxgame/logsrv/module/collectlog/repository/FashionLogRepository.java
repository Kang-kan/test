package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.FashionLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 时装日志
 * @author gil
 *
 */
public interface FashionLogRepository extends JpaRepository<FashionLog, Long> {

}
