package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家信息日志
 * @author gil
 *
 */
@Entity(name = "player_info_log")
@Table(indexes = @Index(name = "playerId_index", columnList = "playerId"))
public class PlayerInfoLog extends BasePlayerLogEntity {

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
	 * 战斗力
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '战斗力'")
	private long power;

	/**
	 * vip等级
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT 'vip等级'")
	private int vipLevel;

	/**
	 * vip经验
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT 'vip经验'")
	private int vipExp;

	/**
	 * 转生修为
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '转生修为'")
	private int rExp;

	/**
	 * 转身等级
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '转身等级'")
	private int rLevel;

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

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getVipExp() {
		return vipExp;
	}

	public void setVipExp(int vipExp) {
		this.vipExp = vipExp;
	}

	public int getrExp() {
		return rExp;
	}

	public void setrExp(int rExp) {
		this.rExp = rExp;
	}

	public int getrLevel() {
		return rLevel;
	}

	public void setrLevel(int rLevel) {
		this.rLevel = rLevel;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
