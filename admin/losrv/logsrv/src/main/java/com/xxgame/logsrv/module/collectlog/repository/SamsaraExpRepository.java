package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.SamsaraExpLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商店积分变化日志
 * @author gil
 *
 */
public interface SamsaraExpRepository extends JpaRepository<SamsaraExpLog, Long> {

}
