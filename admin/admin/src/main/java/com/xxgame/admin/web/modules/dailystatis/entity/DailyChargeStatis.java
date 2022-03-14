package com.xxgame.admin.web.modules.dailystatis.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 充值统计
 * @author gil
 *
 */
@Entity(name = "daily_charge_statis")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId,channelId"))
public class DailyChargeStatis {

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
	 * 日期
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '日期'")
	private int daily;

	/**
	 * 充值人次
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '充值人次'")
	private int chargeCount;

	/**
	 * 充值人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '充值人数'")
	private int chargePlayer;

	/**
	 * 充值金额
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '充值金额'")
	private int chargeAmout;

	/**
	 * 当天首充
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '当天首充'")
	private int todyAmout;

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

	public int getChargePlayer() {
		return chargePlayer;
	}

	public void setChargePlayer(int chargePlayer) {
		this.chargePlayer = chargePlayer;
	}

	public int getChargeAmout() {
		return chargeAmout;
	}

	public void setChargeAmout(int chargeAmout) {
		this.chargeAmout = chargeAmout;
	}

	public int getTodyAmout() {
		return todyAmout;
	}

	public void setTodyAmout(int todyAmout) {
		this.todyAmout = todyAmout;
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
