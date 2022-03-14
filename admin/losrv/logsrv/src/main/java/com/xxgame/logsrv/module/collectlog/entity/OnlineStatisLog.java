package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 在线日志统计
 * @author gil
 *
 */
@Entity(name = "online_statis_log")
@Table(indexes = @Index(name = "server_time_idx", columnList = "time,serverId"))
public class OnlineStatisLog {

	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
    private long id;

	/**
	 * 日志时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '日志时间'")
	private long time;

	/**
	 * 服务器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
	private int serverId;

	/**
	 * 渠道
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '渠道'")
	private String channelId;

	/**
	 * 在线人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '在线人数'")
	private int count;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
