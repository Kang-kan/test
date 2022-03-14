package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家礼包码日志
 * @author gil
 *
 */
@Entity(name = "serial_gift_log")
@Table(indexes = { @Index(name = "player_idx", columnList = "playerId"),
		@Index(name = "cdKey_idx", columnList = "cdKey") })
public class SerialGiftLog extends BasePlayerLogEntity {

	/**
	 * cdKey
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT 'cdKey'")
	private String cdKey;

	/**
	 * 奖励内容
	 */
	@Column(columnDefinition = "VARCHAR(1024) NOT NULL COMMENT '奖励内容'")
	private String bonus;

	public String getCdKey() {
		return cdKey;
	}

	public void setCdKey(String cdKey) {
		this.cdKey = cdKey;
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
