package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 登录日志
 * @author gil
 *
 */
@Entity(name = "login_log")
@Table(indexes = { @Index(name = "summary_idx", columnList = "time,serverId,channelId"),
		@Index(name = "player_idx", columnList = "playerId") })
public class LoginLog extends BasePlayerLogEntity {

	/**
	 * 上次登录IP
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '上次登录IP'")
	private String lastIP;

	/**
	 * 战斗力
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT '战斗力'")
	private long power;

	/**
	 * 是否当天注册
	 */
	@Column(columnDefinition = "TINYINT(1) NOT NULL COMMENT '是否当天注册'")
	private boolean registDay;

	/**
	 * 系统输出的日志
	 */
	@Column(columnDefinition = "TINYINT(1) NOT NULL COMMENT '系统输出的日志'")
	private boolean sysLog;

	public String getLastIP() {
		return lastIP;
	}

	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public boolean isRegistDay() {
		return registDay;
	}

	public void setRegistDay(boolean registDay) {
		this.registDay = registDay;
	}

	public boolean isSysLog() {
		return sysLog;
	}

	public void setSysLog(boolean sysLog) {
		this.sysLog = sysLog;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
