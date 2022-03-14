package com.xxgame.logsrv.module.collectlog.repository;

import com.xxgame.logsrv.module.collectlog.entity.SkillBookLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 技能心法日志
 * @author gil
 *
 */
public interface SkillBookLogRepository extends JpaRepository<SkillBookLog, Long> {

}
