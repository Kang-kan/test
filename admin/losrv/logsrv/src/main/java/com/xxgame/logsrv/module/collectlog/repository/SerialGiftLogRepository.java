package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.SerialGiftLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 礼包码日志
 * @author gil
 *
 */
public interface SerialGiftLogRepository extends JpaRepository<SerialGiftLog, Long> {

}
