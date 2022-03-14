package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家异闻录日志
 * @author gil
 *
 */
@Entity(name = "pokedex_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId,time"))
public class PokedexLog extends BasePlayerLogEntity {

	/**
	 * 操作，0-激活，1-升级
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 异闻录id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '异闻录id'")
	private int pokedexId;

	/**
	 * 异闻录等级
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '异闻录等级'")
	private int pokedexLevel;

	/** 消耗 */
	@Column(columnDefinition = "VARCHAR(256) COMMENT '消耗'")
	private String cost;

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public int getPokedexId() {
		return pokedexId;
	}

	public void setPokedexId(int pokedexId) {
		this.pokedexId = pokedexId;
	}

	public int getPokedexLevel() {
		return pokedexLevel;
	}

	public void setPokedexLevel(int pokedexLevel) {
		this.pokedexLevel = pokedexLevel;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
