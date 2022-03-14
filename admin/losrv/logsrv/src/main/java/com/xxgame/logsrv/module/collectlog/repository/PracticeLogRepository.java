package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.PracticeLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 伙伴、圣魂、守护兽日志
 * @author gil
 *
 */
public interface PracticeLogRepository extends JpaRepository<PracticeLog, Long> {

}
