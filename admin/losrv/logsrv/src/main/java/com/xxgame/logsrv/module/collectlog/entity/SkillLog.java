package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家技能日志
 * @author gil
 *
 */
@Entity(name = "skill_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class SkillLog extends BasePlayerLogEntity {

	/**
	 * 英雄下标
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄下标'")
	private int heroConfigId;

	/**
	 * 技能格下标
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '技能格下标'")
	private int skillIndex;

	/**
	 * 技能等级
	 */
	@Column(columnDefinition = "SMALLINT NOT NULL COMMENT '技能等级'")
	private int skillLevel;

	/**
	 * 消耗
	 */
	@Column(columnDefinition = "VARCHAR(512) COMMENT '消耗 '")
	private String cost;

	public int getHeroConfigId() {
		return heroConfigId;
	}

	public void setHeroConfigId(int heroConfigId) {
		this.heroConfigId = heroConfigId;
	}

	public int getSkillIndex() {
		return skillIndex;
	}

	public void setSkillIndex(int skillIndex) {
		this.skillIndex = skillIndex;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
