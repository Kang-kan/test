package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 混元珠日志
 * @author gil
 *
 */
@Entity(name = "hyz_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class HyzLog extends BasePlayerLogEntity {

	/**
	 * 操作，0-激活，1-杀怪奖励，2-领取额外奖励
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * boss击杀数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'boss击杀数'")
	private int killCount;

	/**
	 * 奖励
	 */
	@Column(columnDefinition = "VARCHAR(512) COMMENT '奖励'")
	private String reward;

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getKillCount() {
		return killCount;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
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
