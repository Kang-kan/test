package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家称号日志
 * @author gil
 *
 */
@Entity(name = "title_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId,time"))
public class TitleLog extends BasePlayerLogEntity {

	/**
	 * 称号id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '称号id'")
	private int titleId;

	/**
	 * 操作，0-获得，1-移除，2-装备，3-卸下
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 过期时间，单位秒。0-永不过期
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '过期时间'")
	private int expireTime;

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
