package com.xxgame.admin.web.modules.onlinestatis.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 在线时长分布
 * @author gil
 *
 */
@Entity(name = "online_statis_dist")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId"))
public class OnlineStatisDist {

	/**
	 * id = daily + serverId
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
	 * 日期
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '日期'")
	private int daily;

	/**
	 * 总人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '总人数'")
	private int total;

	/**
	 * 5分钟
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '5分钟'")
	private int minute5;

	/**
	 * 15分钟
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '15分钟'")
	private int minute15;

	/**
	 * 30分钟
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '30分钟'")
	private int minute30;

	/**
	 * 60分钟
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '60分钟'")
	private int minute60;

	/**
	 * 1小时
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '1小时'")
	private int hour1;

	/**
	 * 3小时以上
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '3小时以上'")
	private int hour3;

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

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getMinute5() {
		return minute5;
	}

	public void setMinute5(int minute5) {
		this.minute5 = minute5;
	}

	public int getMinute15() {
		return minute15;
	}

	public void setMinute15(int minute15) {
		this.minute15 = minute15;
	}

	public int getMinute30() {
		return minute30;
	}

	public void setMinute30(int minute30) {
		this.minute30 = minute30;
	}

	public int getMinute60() {
		return minute60;
	}

	public void setMinute60(int minute60) {
		this.minute60 = minute60;
	}

	public int getHour1() {
		return hour1;
	}

	public void setHour1(int hour1) {
		this.hour1 = hour1;
	}

	public int getHour3() {
		return hour3;
	}

	public void setHour3(int hour3) {
		this.hour3 = hour3;
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
