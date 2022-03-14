package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.MailLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 邮件日志
 * @author gil
 *
 */
public interface MailLogRepository extends JpaRepository<MailLog, Long> {

}
