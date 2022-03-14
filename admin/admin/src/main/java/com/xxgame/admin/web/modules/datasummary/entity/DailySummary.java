package com.xxgame.admin.web.modules.datasummary.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 每日汇总
 * @author gil
 *
 */
@Entity(name = "daily_summary")
@Table(indexes = @Index(name = "summary_idx", columnList = "daily,serverId,channelId"))
public class DailySummary {

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
	 * 创建角色数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '创建角色数'")
	private int createPlayer;

	/**
	 * 登录人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '登录人数'")
	private int loginPlayer;

	/**
	 * 最高在线
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '最高在线'")
	private int maxOnline;

	/**
	 * 最低在线
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '最低在线'")
	private int minOnline;

	/**
	 * 平均在线
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '平均在线'")
	private int avgOnline;

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
	private int chargeAmount;

	/**
	 * arpu
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'arpu'")
	private int arpu;

	/**
	 * arppu
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'arppu'")
	private int arppu;

	/**
	 * 当日付费渗透率
	 */
	@Column(columnDefinition = "FLOAT NOT NULL COMMENT '当日付费渗透率'")
	private float chargeRate;

	/**
	 * 新增用户充值点击次数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '新增用户充值点击次数'")
	private int newChargeClickCount;

	/**
	 * 新增用户充值点击人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '新增用户充值点击人数'")
	private int newChargeClickPlayer;

	/**
	 * 新增用户点击付费率
	 */
	@Column(columnDefinition = "FLOAT NOT NULL COMMENT '新增用户点击付费率'")
	private float newChargeClickRate;

	/**
	 * 新增用户付费人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '新增用户付费人数'")
	private int newChargePlayer;

	/**
	 * 新增用户充值金额
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '新增用户充值金额'")
	private int newChargeAmount;

	/**
	 * 新增付费率
	 */
	@Column(columnDefinition = "FLOAT NOT NULL COMMENT '新增付费率'")
	private float newChargeRate;

	/**
	 * 新增用户ARPU
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '新增用户ARPU'")
	private int newArpu;

	/**
	 * 老用户付费人数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '老用户付费人数'")
	private int oldChargePlayer;

	/**
	 * 老用户充值金额
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '老用户充值金额'")
	private int oldChargeAmount;

	/**
	 * 老用户付费率
	 */
	@Column(columnDefinition = "FLOAT NOT NULL COMMENT '老用户付费率'")
	private float oldChargeRate;

	/**
	 * 老用户ARPU
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '老用户ARPU'")
	private int oldArpu;

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

	public int getCreatePlayer() {
		return createPlayer;
	}

	public void setCreatePlayer(int createPlayer) {
		this.createPlayer = createPlayer;
	}

	public int getLoginPlayer() {
		return loginPlayer;
	}

	public void setLoginPlayer(int loginPlayer) {
		this.loginPlayer = loginPlayer;
	}

	public int getMaxOnline() {
		return maxOnline;
	}

	public void setMaxOnline(int maxOnline) {
		this.maxOnline = maxOnline;
	}

	public int getMinOnline() {
		return minOnline;
	}

	public void setMinOnline(int minOnline) {
		this.minOnline = minOnline;
	}

	public int getAvgOnline() {
		return avgOnline;
	}

	public void setAvgOnline(int avgOnline) {
		this.avgOnline = avgOnline;
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

	public int getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(int chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public int getArpu() {
		return arpu;
	}

	public void setArpu(int arpu) {
		this.arpu = arpu;
	}

	public int getArppu() {
		return arppu;
	}

	public void setArppu(int arppu) {
		this.arppu = arppu;
	}

	public float getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(float chargeRate) {
		this.chargeRate = chargeRate;
	}

	public int getNewChargeClickCount() {
		return newChargeClickCount;
	}

	public void setNewChargeClickCount(int newChargeClickCount) {
		this.newChargeClickCount = newChargeClickCount;
	}

	public int getNewChargeClickPlayer() {
		return newChargeClickPlayer;
	}

	public void setNewChargeClickPlayer(int newChargeClickPlayer) {
		this.newChargeClickPlayer = newChargeClickPlayer;
	}

	public float getNewChargeClickRate() {
		return newChargeClickRate;
	}

	public void setNewChargeClickRate(float newChargeClickRate) {
		this.newChargeClickRate = newChargeClickRate;
	}

	public int getNewChargePlayer() {
		return newChargePlayer;
	}

	public void setNewChargePlayer(int newChargePlayer) {
		this.newChargePlayer = newChargePlayer;
	}

	public int getNewChargeAmount() {
		return newChargeAmount;
	}

	public void setNewChargeAmount(int newChargeAmount) {
		this.newChargeAmount = newChargeAmount;
	}

	public float getNewChargeRate() {
		return newChargeRate;
	}

	public void setNewChargeRate(float newChargeRate) {
		this.newChargeRate = newChargeRate;
	}

	public int getNewArpu() {
		return newArpu;
	}

	public void setNewArpu(int newArpu) {
		this.newArpu = newArpu;
	}

	public int getOldChargePlayer() {
		return oldChargePlayer;
	}

	public void setOldChargePlayer(int oldChargePlayer) {
		this.oldChargePlayer = oldChargePlayer;
	}

	public int getOldChargeAmount() {
		return oldChargeAmount;
	}

	public void setOldChargeAmount(int oldChargeAmount) {
		this.oldChargeAmount = oldChargeAmount;
	}

	public float getOldChargeRate() {
		return oldChargeRate;
	}

	public void setOldChargeRate(float oldChargeRate) {
		this.oldChargeRate = oldChargeRate;
	}

	public int getOldArpu() {
		return oldArpu;
	}

	public void setOldArpu(int oldArpu) {
		this.oldArpu = oldArpu;
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
