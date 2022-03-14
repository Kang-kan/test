package com.xxgame.admin.web.modules.levelstatis.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 每日等级流失统计
 * @author gil
 *
 */
@Entity(name = "daily_level_loss_statis")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId"))
public class DailyLevelLossStatis {

	/**
	 * id = daily + serverId + level
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
	 * 等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '等级'")
	private int level;

	/**
	 * 玩家总人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '玩家总人数'")
	private int count;

	/**
	 * 当前等级玩家人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '当前等级玩家人数'")
	private int levelCount;

	/**
	 * 24小时流失人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '24小时流失人数'")
	private int loss24;

	/**
	 * 3天流失人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '3天流失人数'")
	private int loss72;

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
	}

	public int getLoss24() {
		return loss24;
	}

	public void setLoss24(int loss24) {
		this.loss24 = loss24;
	}

	public int getLoss72() {
		return loss72;
	}

	public void setLoss72(int loss72) {
		this.loss72 = loss72;
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
