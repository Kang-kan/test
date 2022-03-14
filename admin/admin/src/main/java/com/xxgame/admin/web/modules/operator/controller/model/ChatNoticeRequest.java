package com.xxgame.admin.web.modules.operator.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 跑马灯公告请求对象
 * 
 * @author gil
 *
 */
@ApiModel(value = "跑马灯公告请求对象")
public class ChatNoticeRequest {

	/**
	 * 服务器id
	 */
	@ApiModelProperty(value = "服务器id列表，为空或空列表，表示全服")
	private List<Integer> serverIds;

	/**
	 * 渠道id
	 */
	@ApiModelProperty(value = "渠道id，为空或空列表，表示全渠道")
	private List<String> channels;

	/**
	 * 时间间隔
	 */
	@ApiModelProperty(value = "时间间隔，单位秒。一次性公告不用传。")
	private int intervalTime;

	/**
	 * 公告内容
	 */
	@ApiModelProperty(value = "公告内容")
	private String notice;

	public List<Integer> getServerIds() {
		return serverIds;
	}

	public void setServerIds(List<Integer> serverIds) {
		this.serverIds = serverIds;
	}

	public List<String> getChannels() {
		return channels;
	}

	public void setChannels(List<String> channels) {
		this.channels = channels;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
