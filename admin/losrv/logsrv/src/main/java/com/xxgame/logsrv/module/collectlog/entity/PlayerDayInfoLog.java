package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家每日状态信息日志
 * @author gil
 *
 */
@Entity(name = "player_day_info_log")
@Table(indexes = @Index(name = "playerId_index", columnList = "playerId"))
public class PlayerDayInfoLog extends BasePlayerLogEntity {

	/**
	 * 当天在线时间秒
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '当天在线时间秒'")
	private int dayOnlineTime;

	/**
	 * 游戏币
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '游戏币'")
	private long money;

	/**
	 * 充值币
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '充值币'")
	private long gold;

	/**
	 * 经验
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '经验'")
	private long exp;

	/**
	 * 转生修为
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '转生修为'")
	private int rExp;

	public int getDayOnlineTime() {
		return dayOnlineTime;
	}

	public void setDayOnlineTime(int dayOnlineTime) {
		this.dayOnlineTime = dayOnlineTime;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public int getrExp() {
		return rExp;
	}

	public void setrExp(int rExp) {
		this.rExp = rExp;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
