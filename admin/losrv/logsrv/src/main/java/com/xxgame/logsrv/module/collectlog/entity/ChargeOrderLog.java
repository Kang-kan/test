package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 充值定单日志
 * @author gil
 *
 */
@Entity(name = "charge_order_log")
@Table(indexes = { @Index(name = "playerId_idx", columnList = "playerId"),
		@Index(name = "summary_idx", columnList = "time,serverId,channelId"),
		@Index(name = "orderId_idx", columnList = "orderId") })
public class ChargeOrderLog {

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
	 * 玩家名
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '玩家名'")
	private String playerName;

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
	 * 等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '等级'")
	private int level;

	/**
	 * 平台
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '平台'")
	private String platName;

	/**
	 * 订单号
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '订单号'")
	private long orderId;

	/**
	 * 订单金额
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '订单金额'")
	private int rmb;

	/**
	 * 元宝
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '元宝'")
	private int gold;

	/**
	 * 商品Id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '商品Id'")
	private int goodsId;

	/**
	 * 第一次充值金额
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '第一次充值金额'")
	private int firstRmb;

	/**
	 * 是否新用户
	 */
	@Column(columnDefinition = "TINYINT(1) NOT NULL COMMENT '是否新用户'")
	private boolean createDay;

	/**
	 * 第三方支付定单号
	 */
	@Column(columnDefinition = "VARCHAR(64) COMMENT '第三方支付定单号'")
	private String transactionId;

	/**
	 * 下单时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '下单时间'")
	private long time;

	/**
	 * 到账时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '到账时间'")
	private long arrivalTime;

	/**
	 * 通知发货时间
	 */
	@Column(columnDefinition = "BIGINT(20) COMMENT '通知发货时间'")
	private long adviceTime;

	/**
	 * 通知游戏服发货返回的结果
	 */
	@Column(columnDefinition = "VARCHAR(16) COMMENT '通知游戏服发货返回的结果'")
	private String gameResultCode;

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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getRmb() {
		return rmb;
	}

	public void setRmb(int rmb) {
		this.rmb = rmb;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getFirstRmb() {
		return firstRmb;
	}

	public void setFirstRmb(int firstRmb) {
		this.firstRmb = firstRmb;
	}

	public boolean isCreateDay() {
		return createDay;
	}

	public void setCreateDay(boolean createDay) {
		this.createDay = createDay;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public long getAdviceTime() {
		return adviceTime;
	}

	public void setAdviceTime(long adviceTime) {
		this.adviceTime = adviceTime;
	}

	public String getGameResultCode() {
		return gameResultCode;
	}

	public void setGameResultCode(String gameResultCode) {
		this.gameResultCode = gameResultCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
