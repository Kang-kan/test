package com.xxgame.admin.web.modules.onlinestatis.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 每日渠道在线统计
 * @author gil
 *
 */
@Entity(name = "daily_channel_online_statis")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId,channelId"))
public class DailyChannelOnlineStatis {

	/**
	 * id = daily + serverId + channelId
	 */
    @Id
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT 'id'")
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
	 * 每小时在线数据
	 */
	@Column(columnDefinition = "VARCHAR(1024) NOT NULL COMMENT '在线数据'")
	private String statis;

	/**
	 * 生成时间
	 */
	@Column(columnDefinition = "DATETIME COMMENT '生成时间'")
	private Date createTime = new Date();

	@Transient
	private List<Integer> statisList = new ArrayList<>();

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

	public String getStatis() {
		return statis;
	}

	public void setStatis(String statis) {
		this.statis = statis;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Integer> getStatisList() {
		return statisList;
	}

	public void setStatisList(List<Integer> statisList) {
		this.statisList = statisList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
