package com.xxgame.admin.web.modules.operator.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 奖励邮件
 * @author gil
 *
 */
@Entity(name = "reward_mail")
@Table(indexes = { @Index(name = "creator_idx", columnList = "creatorId") })
public class RewardMail {

	/**
	 * id
	 */
    @Id
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
    private long id;
    
    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(32) DEFAULT '' COMMENT '备注'")
    private String remark;


	/**
	 * 类型，1-全服邮件，2-个人邮件
	 */
	@Column(columnDefinition = "SMALLINT DEFAULT '0' COMMENT '类型'")
	private int type;

	/**
	 * 创建者
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '创建者'")
	private long creatorId;

	/**
	 * 服务器id列表
	 */
	@Column(columnDefinition = "VARCHAR(4096) DEFAULT '' COMMENT '服务器id列表'")
	private String serverIds;

	/**
	 * 玩家列表
	 */
	@Column(columnDefinition = "VARCHAR(4096) DEFAULT '' COMMENT '玩家列表'")
	private String players;

	/**
	 * 邮件标题
	 */
	@Column(columnDefinition = "VARCHAR(128) DEFAULT '' COMMENT '邮件标题'")
	private String title;

	/**
	 * 邮件内容
	 */
	@Column(columnDefinition = "VARCHAR(1024) DEFAULT '' COMMENT '邮件内容'")
	private String content;

	/**
	 * 奖励
	 */
	@Column(columnDefinition = "VARCHAR(1024) DEFAULT '' COMMENT '奖励'")
	private String rewards;

	/**
	 * 开始时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '开始时间'")
	private long startTime;

	/**
	 * 结束时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '结束时间'")
	private long endTime;

	/**
	 * 失败的服务器id列表
	 */
	@Column(columnDefinition = "VARCHAR(4096) DEFAULT '' COMMENT '失败的服务器id列表'")
	private String failSrvIds;

	/**
	 * 审核人
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '审核人'")
	private long reviewerId;

	/**
	 * 审核时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '审核时间'")
	private long reviewTime;

	/**
	 * 发送者
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '发送者'")
	private long senderId;

	/**
	 * 发送时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '发送时间'")
	private long sendTime;

	/**
	 * 创建时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '创建时间'")
	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public String getServerIds() {
		return serverIds;
	}

	public void setServerIds(String serverIds) {
		this.serverIds = serverIds;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getFailSrvIds() {
		return failSrvIds;
	}

	public void setFailSrvIds(String failSrvIds) {
		this.failSrvIds = failSrvIds;
	}

	public long getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(long reviewerId) {
		this.reviewerId = reviewerId;
	}

	public long getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(long reviewTime) {
		this.reviewTime = reviewTime;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}