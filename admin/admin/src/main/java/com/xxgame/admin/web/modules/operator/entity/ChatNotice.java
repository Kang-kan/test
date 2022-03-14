package com.xxgame.admin.web.modules.operator.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 跑马灯公告
 * @author gil
 *
 */
@Entity(name = "chat_notice")
public class ChatNotice {

	/**
	 * id
	 */
    @Id
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT 'id'")
    private long id;

	/**
	 * 服务器id列表
	 */
	@Column(columnDefinition = "VARCHAR(4096) DEFAULT '' COMMENT '服务器id列表'")
	private String serverIds;

	/**
	 * 渠道id
	 */
	@Column(columnDefinition = "VARCHAR(512) DEFAULT '' COMMENT '渠道'")
	private String channels;

    /**
     * 内容
     */
    @Column(columnDefinition = "VARCHAR(2048) DEFAULT '' COMMENT '内容'")
    private String notice;

	/**
	 * 时间间隔
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '时间间隔'")
	private int intervalTime;

	/**
	 * 更新者
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '更新者'")
	private long updaterId;

	/**
	 * 更新时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '更新时间'")
	private long updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServerIds() {
		return serverIds;
	}

	public void setServerIds(String serverIds) {
		if (StringUtils.isBlank(serverIds)) {
			serverIds = "";
		}
		this.serverIds = serverIds;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		if (StringUtils.isBlank(channels)) {
			channels = "";
		}
		this.channels = channels;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	public long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(long updaterId) {
		this.updaterId = updaterId;
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