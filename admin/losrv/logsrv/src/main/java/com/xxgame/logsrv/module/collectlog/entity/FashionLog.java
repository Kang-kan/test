package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家时装日志
 * @author gil
 *
 */
@Entity(name = "fashion_log")
@Table(indexes = @Index(name = "playerId_index", columnList = "playerId"))
public class FashionLog extends BasePlayerLogEntity {

	/**
	 * 英雄配置id
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄配置id'")
	private int heroConfigId;

	/**
	 * 操作，0-激活，1-删除，2-穿戴，3-卸下
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 时装id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '时装id'")
	private int fashionId;

	/**
	 * 等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '等级'")
	private int fashionLevel;

	/**
	 * 到期时间 0-永久
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '到期时间'")
	private long overTime;

	/**
	 * 是否穿戴 1 穿 ，0未穿
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '是否穿戴'")
	private byte weared;

	public int getHeroConfigId() {
		return heroConfigId;
	}

	public void setHeroConfigId(int heroConfigId) {
		this.heroConfigId = heroConfigId;
	}

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getFashionId() {
		return fashionId;
	}

	public void setFashionId(int fashionId) {
		this.fashionId = fashionId;
	}

	public int getFashionLevel() {
		return fashionLevel;
	}

	public void setFashionLevel(int fashionLevel) {
		this.fashionLevel = fashionLevel;
	}

	public long getOverTime() {
		return overTime;
	}

	public void setOverTime(long overTime) {
		this.overTime = overTime;
	}

	public byte getWeared() {
		return weared;
	}

	public void setWeared(byte weared) {
		this.weared = weared;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
