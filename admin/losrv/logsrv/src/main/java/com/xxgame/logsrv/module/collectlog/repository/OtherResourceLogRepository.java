package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.OtherResourceLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 其它货币资源日志
 * @author gil
 *
 */
public interface OtherResourceLogRepository extends JpaRepository<OtherResourceLog, Long> {

}
