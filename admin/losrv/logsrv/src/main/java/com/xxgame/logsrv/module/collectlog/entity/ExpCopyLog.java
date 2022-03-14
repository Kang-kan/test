package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 经验副本领取日志
 * @author gil
 *
 */
@Entity(name = "exp_copy_log")
@Table(indexes = @Index(name = "time_srv_idx", columnList = "time,serverId"))
public class ExpCopyLog {

	/**
	 * id
	 */
	@Id
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
	private long id;

	/**
	 * 账号
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '账号'")
	private String account;

	/**
	 * 玩家id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'playerId'")
	private long playerId;

	/**
	 * 渠道id
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '渠道id'")
	private String channelId;

	/**
	 * serverId
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'serverId'")
	private int serverId;

	/**
	 * 倍数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '倍数'")
	private int multiple;

	/**
	 * 时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '时间'")
	private long time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
