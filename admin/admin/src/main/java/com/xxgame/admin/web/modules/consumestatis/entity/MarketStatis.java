package com.xxgame.admin.web.modules.consumestatis.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 商城消耗统计
 * @author gil
 *
 */
@Entity(name = "market_statis")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId"))
public class MarketStatis {

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
	 * 购买总人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '购买总人数'")
	private int totalPlayerCount;

	/**
	 * 购买物品总次数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '购买总人数'")
	private int totalTimes;

	/**
	 * 购买总额
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '购买总额'")
	private int totalAmount;

	/**
	 * 物品id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '物品id'")
	private int itemId;

	/**
	 * 购物物品数据
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '购物物品数据'")
	private int itemCount;

	/**
	 * 购物物品名
	 */
	@Column(columnDefinition = "VARCHAR(16) NOT NULL COMMENT '购物物品名'")
	private String itemName;

	/**
	 * 人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '购物物品名'")
	private int playerCount;

	/**
	 * 购买物品次数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '购买物品次数'")
	private int times;

	/**
	 * 货币类型
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '货币类型'")
	private int currency;

	/**
	 * 花费金额
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '花费金额'")
	private int amount;

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

	public int getTotalPlayerCount() {
		return totalPlayerCount;
	}

	public void setTotalPlayerCount(int totalPlayerCount) {
		this.totalPlayerCount = totalPlayerCount;
	}

	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
