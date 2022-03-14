package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家离线挂机日志
 * @author gil
 *
 */
@Entity(name = "offline_reward_log")
@Table(indexes = @Index(name = "playerId_index", columnList = "playerId"))
public class OfflineRewardLog extends BasePlayerLogEntity {

	/**
	 * 离线间隔
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '离线间隔'")
	private long gap;

	/**
	 * 奖励内容
	 */
	@Column(columnDefinition = "VARCHAR(1024) NOT NULL COMMENT '奖励内容'")
	private String bonus;

	public long getGap() {
		return gap;
	}

	public void setGap(long gap) {
		this.gap = gap;
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
