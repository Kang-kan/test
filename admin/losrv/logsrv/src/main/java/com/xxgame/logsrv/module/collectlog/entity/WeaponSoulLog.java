package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 玩家器灵日志
 * @author gil
 *
 */
@Entity(name = "weapon_soul_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId"))
public class WeaponSoulLog extends BasePlayerLogEntity {

	/**
	 * 英雄配置id
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '英雄配置id'")
	private int heroConfigId;

	/**
	 * 操作，0-穿戴，1-升级，2-熔炼，3-合成
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 槽位
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '槽位'")
	private int weaponIndex;

	/**
	 * 装备id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '装备id'")
	private int equipId;

	/**
	 * 消耗
	 */
	@Column(columnDefinition = "VARCHAR(512) COMMENT '消耗 '")
	private String cost;

	/**
	 * 增加熔炼值
	 */
	@Column(columnDefinition = "SMALLINT NOT NULL COMMENT '增加熔炼值'")
	private int elfExp;

	/**
	 * 熔炼旧的装备id
	 */
	@Column(columnDefinition = "VARCHAR(1024) COMMENT '熔炼旧的装备id'")
	private String deleteEquipIds;

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

	public int getWeaponIndex() {
		return weaponIndex;
	}

	public void setWeaponIndex(int weaponIndex) {
		this.weaponIndex = weaponIndex;
	}

	public int getEquipId() {
		return equipId;
	}

	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public int getElfExp() {
		return elfExp;
	}

	public void setElfExp(int elfExp) {
		this.elfExp = elfExp;
	}

	public String getDeleteEquipIds() {
		return deleteEquipIds;
	}

	public void setDeleteEquipIds(String deleteEquipIds) {
		this.deleteEquipIds = deleteEquipIds;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
