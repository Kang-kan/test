package com.xxgame.admin.web.modules.datasummary.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 月活跃数据
 * @author gil
 *
 */
@Entity(name = "monthly_active")
@Table(indexes = @Index(name = "summary_idx", columnList = "month,serverId,channelId"))
public class MonthlyActive {

	/**
	 * id = month + serverId + channelId
	 */
    @Id
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT 'id'")
    private String id;

	/**
	 * 服务器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
	private int serverId;

	/**
	 * 渠道id
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '渠道id'")
	private String channelId;

	/**
	 * 月份
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '月份'")
	private int month;

	/**
	 * 本月户数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '本月户数'")
	private int monthPlayer;

	/**
	 * 活跃用户数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '活跃用户数'")
	private int monthActive;

	/**
	 * 生成时间
	 */
	@Column(columnDefinition = "DATETIME COMMENT '生成时间'")
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonthPlayer() {
		return monthPlayer;
	}

	public void setMonthPlayer(int monthPlayer) {
		this.monthPlayer = monthPlayer;
	}

	public int getMonthActive() {
		return monthActive;
	}

	public void setMonthActive(int monthActive) {
		this.monthActive = monthActive;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
