package com.xxgame.admin.web.modules.gameserver.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 游戏服配置
 * @author gil
 *
 */
@Entity(name = "game_server")
@Table(indexes = { @Index(name = "openTime_idx", columnList = "openTime") })
public class GameServer {
	
	/**
	 * 服务器id
	 */
    @Id
    @Column(columnDefinition = "INT NOT NULL COMMENT '服务器id'")
    private int id;
    
    /**
     * 服务器名
     */
    @Column(columnDefinition = "VARCHAR(32) DEFAULT '' COMMENT '服务器名'")
    private String name;

	/**
	 * 类型，0-正式服，1-测试服，2-审核服
	 */
	@Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '类型'")
	private int type;

	/**
	 * 内网ip
	 */
	@Column(columnDefinition = "VARCHAR(32) DEFAULT '' COMMENT '内网ip'")
	private String localIp;

	/**
	 * 开服时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '开服时间'")
	private long openTime;

	/**
	 * 定时开服时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '计划开服时间'")
	private long schedOpenTime;

	/**
	 * 端口
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '端口'")
	private int port;

	/**
	 * 充值端口
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '充值端口'")
	private int chargePort;

	/**
	 * GM端口
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT 'GM端口'")
	private int gmPort;

	/**
	 * 合服时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '合服时间'")
	private long mergeTime;

	/**
	 * 合服后服务器id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '合服后服务器id'")
	private int mergeSrvId;

	/**
	 * 平台id
	 */
	@Column(columnDefinition = "INT NOT NULL COMMENT '平台id'")
	private int operatorId;

	/**
	 * 更新时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '更新时间'")
	private long updateTime;

	/**
	 * 创建时间
	 */
	@Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '创建时间'")
	private long createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public long getOpenTime() {
		return openTime;
	}

	public void setOpenTime(long openTime) {
		this.openTime = openTime;
	}

	public long getSchedOpenTime() {
		return schedOpenTime;
	}

	public void setSchedOpenTime(long schedOpenTime) {
		this.schedOpenTime = schedOpenTime;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getChargePort() {
		return chargePort;
	}

	public void setChargePort(int chargePort) {
		this.chargePort = chargePort;
	}

	public int getGmPort() {
		return gmPort;
	}

	public void setGmPort(int gmPort) {
		this.gmPort = gmPort;
	}

	public long getMergeTime() {
		return mergeTime;
	}

	public void setMergeTime(long mergeTime) {
		this.mergeTime = mergeTime;
	}

	public int getMergeSrvId() {
		return mergeSrvId;
	}

	public void setMergeSrvId(int mergeSrvId) {
		this.mergeSrvId = mergeSrvId;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * 是否已开服
	 * @return
	 */
	@JSONField(serialize = false)
	public boolean isOpen() {
		return this.openTime > 0 && this.openTime <= System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}