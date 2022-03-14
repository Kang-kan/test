package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 玩家信息统计
 * @author gil
 *
 */
@Entity(name = "player_statis_log")
@Table(indexes = @Index(name = "daily_idx", columnList = "daily,serverId"))
public class PlayerStatisLog {

	/**
	 * id = daily + serverId
	 */
	@Id
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT 'id'")
	private String id;
	
	/**
	 * 日期
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '日期'")
	private int daily;

	/**
	 * 服务器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
	private int serverId;

	/**
	 * 总人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '总人数'")
	private int total;

	/**
	 * 平台
	 */
	@Column(columnDefinition = "TEXT COMMENT '平台'")
	private String levelMap;

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

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getLevelMap() {
		return levelMap;
	}

	public void setLevelMap(String levelMap) {
		this.levelMap = levelMap;
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
