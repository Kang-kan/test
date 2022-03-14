package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.MeridianLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 星宿日志
 * @author gil
 *
 */
public interface MeridianLogRepository extends JpaRepository<MeridianLog, Long> {

}
