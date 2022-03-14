package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.RExpLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 转生修改变化日志
 * @author gil
 *
 */
public interface RExpLogRepository extends JpaRepository<RExpLog, Long> {

}
