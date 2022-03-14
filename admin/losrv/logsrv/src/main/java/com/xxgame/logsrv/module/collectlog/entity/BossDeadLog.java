package com.xxgame.logsrv.module.collectlog.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 玩家击杀boss日志
 * @author gil
 *
 */
@Entity(name = "boss_deal_log")
@Table(indexes = { @Index(name = "owner_idx", columnList = "ownerId,ownerType,playType,time"),
		@Index(name = "battleId_idx", columnList = "battleId"),
		@Index(name = "time_server_type_idx", columnList = "time,battleServer,playType") })
public class BossDeadLog {

	/**
	 * id
	 */
	@Id
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'id'")
	private long id;

	/**
	 * 玩法，1-活动boss，2-奇遇boss，3-召唤boss，4-印记塔，5-仙盟试炼，6-天域争霸，7-帝君神殿，8-帝君，9-帝君塔，10-秘境boss，
	 * 		11-boss之家， 12-仙戒boss，13-主线boss，14-关卡协助，  15-结婚场景，16-密境boss，17-守卫剑阁，18-世界boss，19-个人boss，20-万妖塔，
	 * 		21-翎羽boss， 22-仙缘副本，23-历练之地，24-仙魔战场，25-九重天，26-仙界魔神，27-荒古禁地
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '玩法'")
	private int playType;

	/**
	 * 战斗id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '战斗id'")
	private long battleId;

	/**
	 * 战斗服务器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
	private int battleServer;

	/**
	 * boss配置id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'boss配置id'")
	private int bossId;

	/**
	 * boss名
	 */
	@Column(columnDefinition = "VARCHAR(64) COMMENT 'boss名'")
	private String bossName;

	/**
	 * 归属类型，0-个人，1-公会
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '归属类型'")
	private int ownerType;

	/**
	 * 归属者id
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '归属都id'")
	private long ownerId;

	/**
	 * 归属者名
	 */
	@Column(columnDefinition = "VARCHAR(64) COMMENT '归属者名'")
	private String ownerName;

	/**
	 * 奖励类型，0-参与，1-归属，2-排名，3-失败，4-破盾
	 */
	@Column(columnDefinition = "TINYINT NOT NULL COMMENT '奖励类型'")
	private int rewardType;

	/**
	 * 奖励内容
	 */
	@Column(columnDefinition = "VARCHAR(1024) COMMENT '奖励内容'")
	private String reward;

	/**
	 * 时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '时间'")
	private long time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPlayType() {
		return playType;
	}

	public void setPlayType(int playType) {
		this.playType = playType;
	}

	public long getBattleId() {
		return battleId;
	}

	public void setBattleId(long battleId) {
		this.battleId = battleId;
	}

	public int getBattleServer() {
		return battleServer;
	}

	public void setBattleServer(int battleServer) {
		this.battleServer = battleServer;
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getRewardType() {
		return rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
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
