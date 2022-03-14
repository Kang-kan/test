package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家分享日志
 * @author gil
 *
 */
@Entity(name = "share_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId,time"))
public class ShareLog extends BasePlayerLogEntity {

	/**
	 * 操作，0-分享，1-领取
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 分享次数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '分享次数'")
	private int shareCount;

	/**
	 * 分享奖励次数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '分享奖励次数'")
	private int shareRewardCount;

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getShareRewardCount() {
		return shareRewardCount;
	}

	public void setShareRewardCount(int shareRewardCount) {
		this.shareRewardCount = shareRewardCount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
