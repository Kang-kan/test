package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 伙伴、圣魂、守护兽日志
 * @author gil
 *
 */
@Entity(name = "practice_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class PracticeLog extends BasePlayerLogEntity {

	/**
	 * 英雄配置id
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄配置id'")
	private int heroConfigId;

	/**
	 * 操作，0-激活，1-激活子功能，2-提升属性等级，3-增加属性经验，4-技能升级，5-装备，6-装备升阶
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 配置id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '配置id'")
	private int configId;

	/**
	 * 子功能配置id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '子功能配置id'")
	private int childConfigId;

	/**
	 * 子功能类型，0-CostItemLevelUp、1-CostItemAddExp、2-CostItemLearnSkill、3-GridConfig
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '子功能类型'")
	private int childType;

	/** 消耗 */
	@Column(columnDefinition = "VARCHAR(512) COMMENT '消耗'")
	private String cost;

	/**
	 * 等级或配置id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '等级或配置id'")
	private int levelId;

	/**
	 * 经验
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '经验'")
	private int exp;

	public int getHeroConfigId() {
		return heroConfigId;
	}

	public void setHeroConfigId(int heroConfigId) {
		this.heroConfigId = heroConfigId;
	}

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public int getChildConfigId() {
		return childConfigId;
	}

	public void setChildConfigId(int childConfigId) {
		this.childConfigId = childConfigId;
	}

	public int getChildType() {
		return childType;
	}

	public void setChildType(int childType) {
		this.childType = childType;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
