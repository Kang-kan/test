package com.xxgame.admin.web.modules.chargestatis.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 付费登录流失
 * @author gil
 *
 */
@Entity(name = "charge_loss_statis")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId,channelId"))
public class ChargeLossStatis {

	/**
	 * id = daily + serverId + channelId
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
	 * 每日
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '每日'")
	private int daily;

	/**
	 * 充值人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '充值人数'")
	private int chargeCount;

	/**
	 * 流失人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '流失人数'")
	private int lostCount;

	/**
	 * 生成时间
	 */
	@Column(columnDefinition = "DATETIME COMMENT '生成时间'")
	private Date createTime = new Date();

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

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
	}

	public int getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(int chargeCount) {
		this.chargeCount = chargeCount;
	}

	public int getLostCount() {
		return lostCount;
	}

	public void setLostCount(int lostCount) {
		this.lostCount = lostCount;
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
