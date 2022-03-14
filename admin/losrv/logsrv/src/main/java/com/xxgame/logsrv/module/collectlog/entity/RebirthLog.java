package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家转生日志
 * @author gil
 *
 */
@Entity(name = "rebirth_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class RebirthLog extends BasePlayerLogEntity {

	/**
	 * 转生前等级
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '转生前等级'")
	private int beforeLevel;

	/**
	 * 消耗
	 */
	@Column(columnDefinition = "VARCHAR(256) NOT NULL COMMENT '消耗 '")
	private String cost;

	public int getBeforeLevel() {
		return beforeLevel;
	}

	public void setBeforeLevel(int beforeLevel) {
		this.beforeLevel = beforeLevel;
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
