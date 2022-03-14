package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家星宿日志
 * @author gil
 *
 */
@Entity(name = "meridian_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class MeridianLog extends BasePlayerLogEntity {

	/**
	 * 英雄配置id
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄配置id'")
	private int heroConfigId;

	/**
	 * 操作，0-升级，1-升阶
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**经脉等级*/
	@Column(columnDefinition = "SMALLINT NOT NULL COMMENT '经脉等级'")
	private int meridianLevel;

	/**经脉阶*/
	@Column(columnDefinition = "SMALLINT NOT NULL COMMENT '经脉阶'")
	private int meridianStep;

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

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getMeridianLevel() {
		return meridianLevel;
	}

	public void setMeridianLevel(int meridianLevel) {
		this.meridianLevel = meridianLevel;
	}

	public int getMeridianStep() {
		return meridianStep;
	}

	public void setMeridianStep(int meridianStep) {
		this.meridianStep = meridianStep;
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
