package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.ItemLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 道具日志
 * @author gil
 *
 */
public interface ItemLogRepository extends JpaRepository<ItemLog, Long> {

}
