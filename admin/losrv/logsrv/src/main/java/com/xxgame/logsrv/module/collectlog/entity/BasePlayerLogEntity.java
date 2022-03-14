package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 基础玩家日志实体
 * @author gil
 *
 */
@MappedSuperclass
public class BasePlayerLogEntity {

	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id'")
    protected long id;

    /**
     * 账号
     */
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '账号'")
	protected String account;

	/**
	 * 玩家id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'playerId'")
	protected long playerId;

	/**
	 * 玩家名
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '玩家名'")
	protected String playerName;

	/**
	 * 等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '等级'")
	protected int level;

	/**
	 * 渠道id
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '渠道id'")
	protected String channelId;

	/**
	 * 平台
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '平台'")
	protected String platName;

	/**
	 * serverId
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'serverId'")
	protected int serverId;

	/**
	 * 时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '时间'")
	protected long time;

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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
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
