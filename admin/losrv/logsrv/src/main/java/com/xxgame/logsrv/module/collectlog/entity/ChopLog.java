package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家神器日志
 * @author gil
 *
 */
@Entity(name = "chop_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class ChopLog extends BasePlayerLogEntity {

	/**
	 * 神器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '神器id'")
	private int chopId;

	/**
	 * 碎片id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '碎片id'")
	private int childId;

	public int getChopId() {
		return chopId;
	}

	public void setChopId(int chopId) {
		this.chopId = chopId;
	}

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
