package com.xxgame.admin.web.modules.customerservice.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 封禁角色
 * @author gil
 *
 */
@Entity(name = "blockPlayer")
@Table(indexes = { @Index(name = "srv_idx", columnList = "serverId,updateTime"),
		@Index(name = "playerId_idx", columnList = "playerId")})
public class BlockPlayer {

	/**
	 * 玩家id
	 */
	@Id
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT '玩家id'")
	private long playerId;

	/**
	 * 服务器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
	private int serverId;

	/**
	 * 账号
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '账号'")
	protected String account;

	/**
	 * 渠道id
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '渠道id'")
	private String channelId;

	/**
	 * 玩家名
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '玩家名'")
	private String playerName;

	/**
	 * 解封时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT '解封时间'")
	private long unBlockTime;

	/**
	 * 原因
	 */
	@Column(columnDefinition = "VARCHAR(2048) COMMENT '原因'")
	private String reason;

	/**
	 * 更新时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '更新时间'")
	private long updateTime;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public long getUnBlockTime() {
		return unBlockTime;
	}

	public void setUnBlockTime(long unBlockTime) {
		this.unBlockTime = unBlockTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}