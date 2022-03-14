package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.TitleLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 称号日志
 * @author gil
 *
 */
public interface TitleLogRepository extends JpaRepository<TitleLog, Long> {

}
