package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 仙盟日志
 * @author gil
 *
 */
@Entity(name = "family_log")
@Table(indexes = { @Index(name = "player_idx", columnList = "opPlayerId,targetPlayerId"),
		@Index(name = "family_idx", columnList = "familyId")} )
public class FamilyLog {

	/**
	 * id
	 */
	@Id
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
	private long id;

	/** 工会编号 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'familyId'")
	private long familyId;

	/** 工会名称 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '工会名称'")
	private String name;

	/** 会长名字 */
	@Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '会长名字'")
	private String leaderName;

	/** 工会经验 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '工会经验'")
	private int exp;

	/** 工会等级 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '工会等级'")
	private int level;

	/**
	 * 操作类型，0-创建，1-加入，2-离开，3-任命，4-职位变化，5-转让，6-弹劾，7-踢人，8-解散，9-捐献，10-批准加入
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '操作类型'")
	private int opValue;

	/** 操作人id */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '操作人id'")
	private long opPlayerId;

	/** 操作人 */
	@Column(columnDefinition = "VARCHAR(32) COMMENT '操作人'")
	private String opPlayerName;

	/** 操作人职位，1-会长，2-副会长，3-长老，4-护法，5-会员，6-非会员 */
	@Column(columnDefinition = "TINYINT NOT NULL DEFAULT '0' COMMENT '操作人职位'")
	private int opPos;

	/** 被操作人id */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '被操作人id'")
	private long targetPlayerId;

	/** 被操作人 */
	@Column(columnDefinition = "VARCHAR(32) COMMENT '被操作人'")
	private String targetPlayerName;

	/** 被操作人职位 */
	@Column(columnDefinition = "TINYINT NOT NULL DEFAULT '0' COMMENT '被操作人职位'")
	private int targetPos;

	/** 时间 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '时间'")
	private long time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(long familyId) {
		this.familyId = familyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getOpValue() {
		return opValue;
	}

	public void setOpValue(int opValue) {
		this.opValue = opValue;
	}

	public long getOpPlayerId() {
		return opPlayerId;
	}

	public void setOpPlayerId(long opPlayerId) {
		this.opPlayerId = opPlayerId;
	}

	public String getOpPlayerName() {
		return opPlayerName;
	}

	public void setOpPlayerName(String opPlayerName) {
		this.opPlayerName = opPlayerName;
	}

	public int getOpPos() {
		return opPos;
	}

	public void setOpPos(int opPos) {
		this.opPos = opPos;
	}

	public long getTargetPlayerId() {
		return targetPlayerId;
	}

	public void setTargetPlayerId(long targetPlayerId) {
		this.targetPlayerId = targetPlayerId;
	}

	public String getTargetPlayerName() {
		return targetPlayerName;
	}

	public void setTargetPlayerName(String targetPlayerName) {
		this.targetPlayerName = targetPlayerName;
	}

	public int getTargetPos() {
		return targetPos;
	}

	public void setTargetPos(int targetPos) {
		this.targetPos = targetPos;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
