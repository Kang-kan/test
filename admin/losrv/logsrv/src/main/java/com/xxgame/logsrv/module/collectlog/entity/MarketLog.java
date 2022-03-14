package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 商城日志
 * @author gil
 *
 */
@Entity(name = "market_log")
@Table(indexes = { @Index(name = "player_idx", columnList = "playerId,time"),
		@Index(name = "statis_idx", columnList = "time,serverId,itemId") })
public class MarketLog {

	/**
	 * id
	 */
    @Id
    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
    private long id;

	/**
	 * 账号
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '账号'")
	private String account;

	/**
	 * 玩家id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'playerId'")
	private long playerId;

	/**
	 * serverId
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'serverId'")
	private int serverId;

	/**
	 * 玩家名
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '玩家名'")
	private String playerName;

	/**
	 * 等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '等级'")
	private int level;

	/**
	 *  道具ID
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '道具ID'")
	private int itemId;

	/**
	 * 道具名字
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '道具名字'")
	private String itemName;

	/**
	 * 物品单价
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '物品单价'")
	private int price;

	/**
	 * 数量
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '数量'")
	private int count;

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
	 * 渠道id
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '渠道id'")
	private String channelId;

	/**
	 * 时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '时间'")
	private long time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
