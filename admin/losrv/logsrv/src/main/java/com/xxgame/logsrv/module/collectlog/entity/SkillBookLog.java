package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家心法技能日志
 * @author gil
 *
 */
@Entity(name = "skill_book_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class SkillBookLog extends BasePlayerLogEntity {

	/**
	 * 英雄配置id
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄配置id'")
	private int heroConfigId;

	/**
	 * 技能格下标
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '技能格下标'")
	private int skillIndex;

	/**
	 * 操作，0-学习，1-升级，2-分解，3-合成
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 技能id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '技能id'")
	private int skillId;

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

	/**
	 * 奖励
	 */
	@Column(columnDefinition = "VARCHAR(512) COMMENT '消耗 '")
	private String reward;

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

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
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

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
