package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.SkillLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 技能日志
 * @author gil
 *
 */
public interface SkillLogRepository extends JpaRepository<SkillLog, Long> {

}
