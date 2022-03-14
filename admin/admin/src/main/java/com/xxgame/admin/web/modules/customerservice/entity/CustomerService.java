package com.xxgame.admin.web.modules.customerservice.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 客服
 * @author gil
 *
 */
@Entity(name = "customer_service")
@Table(indexes = { @Index(name = "srv_channel_idx", columnList = "serverId,channelId,playerId"),
		@Index(name = "playerId_idx", columnList = "playerId")})
public class CustomerService {

	/**
	 * id
	 */
    @Id
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
    private long id;

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
	 * 状态
	 */
	@Column(columnDefinition = "SMALLINT NOT NULL COMMENT '状态'")
	private int status;

	/**
	 * 玩家id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT '玩家id'")
	private long playerId;

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
	 * vip等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'vip等级'")
	private int vipLevel;

	/**
	 * ip
	 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT 'ip'")
	private String ip;

	/**
	 * 内容
	 */
	@Column(columnDefinition = "VARCHAR(512) NOT NULL COMMENT '内容'")
	private String context;

	/**
	 * 回复者id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT '回复者id'")
	private long replier;

	/**
	 * 回复标题
	 */
	@Column(columnDefinition = "VARCHAR(32) COMMENT '回复标题'")
	private String replyTitle;

	/**
	 * 回复内容
	 */
	@Column(columnDefinition = "VARCHAR(512) COMMENT '回复内容'")
	private String reply;

	/**
	 * 生成时间
	 */
	@Column(columnDefinition = "DATETIME COMMENT '生成时间'")
	private Date createTime = new Date();

	/**
	 * 回复时间
	 */
	@Column(columnDefinition = "DATETIME COMMENT '回复时间'")
	private Date replyTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public long getReplier() {
		return replier;
	}

	public void setReplier(long replier) {
		this.replier = replier;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
