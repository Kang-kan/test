package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.PokedexLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 异闻录日志
 * @author gil
 *
 */
public interface PokedexLogRepository extends JpaRepository<PokedexLog, Long> {

}
