package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 仙兽灵感日志
 * @author gil
 *
 */
@Entity(name = "totem_expand_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class TotemExpandLog extends BasePlayerLogEntity {

	/**
	 * 英雄配置id
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄配置id'")
	private int heroConfigId;

	/**
	 * 灵感id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '灵感id'")
	private int expandId;

	/**
	 * 灵感等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '灵感等级'")
	private int expandLevel;

	/**
	 * 灵感技能index
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '灵感技能index'")
	private int skillIndex;

	/**
	 * 灵感次数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '灵感次数'")
	private int expandBase;

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

	public int getExpandId() {
		return expandId;
	}

	public void setExpandId(int expandId) {
		this.expandId = expandId;
	}

	public int getExpandLevel() {
		return expandLevel;
	}

	public void setExpandLevel(int expandLevel) {
		this.expandLevel = expandLevel;
	}

	public int getSkillIndex() {
		return skillIndex;
	}

	public void setSkillIndex(int skillIndex) {
		this.skillIndex = skillIndex;
	}

	public int getExpandBase() {
		return expandBase;
	}

	public void setExpandBase(int expandBase) {
		this.expandBase = expandBase;
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
