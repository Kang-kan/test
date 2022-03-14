package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.ExpCopyLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 经验副本领取日志
 * @author gil
 *
 */
public interface ExpCopyLogRepository extends JpaRepository<ExpCopyLog, Long> {

}
