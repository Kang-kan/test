package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 翅膀日志
 * @author gil
 *
 */
@Entity(name = "wing_log")
@Table(indexes = @Index(name = "playerId_index", columnList = "playerId"))
public class WingLog extends BasePlayerLogEntity {

	/**
	 * 英雄下标
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '英雄下标'")
	private int heroIndex;

	/**
	 * 翅膀等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '翅膀等级'")
	private int winglevel;

	/**
	 * 翅膀总星数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '翅膀总星数'")
	private int wingstar;

	/**
	 * 变化类型
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '变化类型'")
	private String functionType;

	public int getHeroIndex() {
		return heroIndex;
	}

	public void setHeroIndex(int heroIndex) {
		this.heroIndex = heroIndex;
	}

	public int getWinglevel() {
		return winglevel;
	}

	public void setWinglevel(int winglevel) {
		this.winglevel = winglevel;
	}

	public int getWingstar() {
		return wingstar;
	}

	public void setWingstar(int wingstar) {
		this.wingstar = wingstar;
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
