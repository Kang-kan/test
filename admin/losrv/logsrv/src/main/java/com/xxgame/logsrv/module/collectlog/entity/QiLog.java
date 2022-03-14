package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 真气增减日志
 * @author gil
 *
 */
@Entity(name = "qi_log")
@Table(indexes = @Index(name = "playerId_index", columnList = "playerId"))
public class QiLog extends BasePlayerLogEntity {

	/**
	 * 当前数量
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '当前数量'")
	private long value;

	/**
	 * 增减数量
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '增减数量'")
	private long changeNum;

	/**
	 * 变化类型
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '变化类型'")
	private String functionType;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(long changeNum) {
		this.changeNum = changeNum;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
