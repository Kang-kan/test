package com.xxgame.admin.web.modules.gameserver.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 服务器管理请求对象
 * 
 * @author gil
 *
 */
@ApiModel(value = "服务器管理请求对象")
public class GameServerRequest {

	/**
	 * 服务器id
	 */
	@ApiModelProperty(value = "服务器id")
	private int id;

	/**
	 * 服务器名
	 */
	@ApiModelProperty(value = "服务器名")
	private String name;

	/**
	 * 内网ip
	 */
	@ApiModelProperty(value = "内网ip，可为空")
	private String localIp;

	/**
	 * 开服时间
	 */
	@ApiModelProperty(value = "开服时间")
	private long openTime;

	/**
	 * 定时开服时间
	 */
	@ApiModelProperty(value = "定时开服时间")
	private long schedOpenTime;

	/**
	 * 端口
	 */
	@ApiModelProperty(value = "端口")
	private int port;

	/**
	 * 充值端口
	 */
	@ApiModelProperty(value = "充值端口")
	private int chargePort;

	/**
	 * GM端口
	 */
	@ApiModelProperty(value = "GM端口")
	private int gmPort;

	/**
	 * 合服时间
	 */
	@ApiModelProperty(value = "合服时间，设置传0")
	private long mergeTime;

	/**
	 * 合服后服务器id
	 */
	@ApiModelProperty(value = "合服后服务器id，设置传0")
	private int mergeSrvId;

	/**
	 * 平台id
	 */
	@ApiModelProperty(value = "平台id")
	private int operatorId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
