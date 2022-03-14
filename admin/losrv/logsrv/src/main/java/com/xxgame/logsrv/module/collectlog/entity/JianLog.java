package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 飞剑日志
 * @author gil
 *
 */
@Entity(name = "jian_log")
@Table(indexes = @Index(name = "playerId_index", columnList = "playerId"))
public class JianLog extends BasePlayerLogEntity {

	/**
	 * 英雄下标
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '英雄下标'")
	private int heroIndex;

	/**
	 * 飞剑等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '飞剑等级'")
	private int jianlevel;

	/**
	 * 飞剑总星数
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '飞剑总星数'")
	private int jianstar;

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

	public int getJianlevel() {
		return jianlevel;
	}

	public void setJianlevel(int jianlevel) {
		this.jianlevel = jianlevel;
	}

	public int getJianstar() {
		return jianstar;
	}

	public void setJianstar(int jianstar) {
		this.jianstar = jianstar;
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
