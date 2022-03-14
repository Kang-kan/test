package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家成就日志
 * @author gil
 *
 */
@Entity(name = "achieve_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId,time"))
public class AchieveLog extends BasePlayerLogEntity {

	/**
	 * 成就ID
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '成就ID'")
	private int achieveId;

	/**
	 * 奖励
	 */
	@Column(columnDefinition = "VARCHAR(1024) COMMENT '奖励'")
	private String bonus;

	public int getAchieveId() {
		return achieveId;
	}

	public void setAchieveId(int achieveId) {
		this.achieveId = achieveId;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
