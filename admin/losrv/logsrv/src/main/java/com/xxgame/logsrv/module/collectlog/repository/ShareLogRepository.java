package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.ShareLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 分享日志
 * @author gil
 *
 */
public interface ShareLogRepository extends JpaRepository<ShareLog, Long> {

}
