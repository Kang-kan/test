package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 道具日志
 * @author gil
 *
 */
@Entity(name = "item_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId,time"))
public class ItemLog extends BasePlayerLogEntity {

	/**
	 * 物品类型
	 */
	@Column(columnDefinition = "VARCHAR(16) NOT NULL COMMENT '物品类型'")
	private String bonusType;

	/**
	 * 物品id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '物品id'")
	private int itemId;

	/**
	 * 物品名字
	 */
	@Column(columnDefinition = "VARCHAR(16) NOT NULL COMMENT '物品名字'")
	private String itemName;

	/**
	 * 增减数量
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT '增减数量'")
	private long count;

	/**
	 * 当前数量
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL COMMENT '当前数量'")
	private long currentCount;

	/**
	 * 变化类型
	 */
	@Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '变化类型'")
	private String functionType;

	public String getBonusType() {
		return bonusType;
	}

	public void setBonusType(String bonusType) {
		this.bonusType = bonusType;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(long currentCount) {
		this.currentCount = currentCount;
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
