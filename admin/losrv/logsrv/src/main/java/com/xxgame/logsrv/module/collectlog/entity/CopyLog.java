package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家副本日志
 * @author gil
 *
 */
@Entity(name = "copy_log")
@Table(indexes = @Index(name = "player_idx", columnList = "playerId,time"))
public class CopyLog extends BasePlayerLogEntity {

	/**
	 * 副本类型，0-材料副本，1-经验副本，2-仙缘副本
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '副本类型'")
	private int copyType;

	/**
	 * 副本id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '副本id'")
	private int copyId;

	/**
	 * 操作，0-进入，1-离开，2-领取奖励，3-扫荡，4-掉落
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作'")
	private int opValue;

	/**
	 * 奖励内容
	 */
	@Column(columnDefinition = "VARCHAR(1024) COMMENT '奖励内容'")
	private String bonus;

	/**
	 * 额外信息
	 */
	@Column(columnDefinition = "VARCHAR(128) COMMENT '额外信息'")
	private String ext;

	public int getCopyType() {
		return copyType;
	}

	public void setCopyType(int copyType) {
		this.copyType = copyType;
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
