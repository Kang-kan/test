package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家强化日志
 * @author gil
 *
 */
@Entity(name = "enhance_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId,time"))
public class EnhanceLog extends BasePlayerLogEntity {

	/**
	 * 英雄配置id
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄配置id'")
	private int heroConfigId;

	/**
	 * 强化类型，0-强化，1-精练，2-灵石
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '强化类型'")
	private int type;

	/**
	 * 槽位
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '槽位'")
	private int enIndex;

	/**
	 * 强化等级
	 */
	@Column(columnDefinition = "SMALLINT NOT NULL COMMENT '强化等级'")
	private int enLevel;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getEnIndex() {
		return enIndex;
	}

	public void setEnIndex(int enIndex) {
		this.enIndex = enIndex;
	}

	public int getEnLevel() {
		return enLevel;
	}

	public void setEnLevel(int enLevel) {
		this.enLevel = enLevel;
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
