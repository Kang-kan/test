package com.xxgame.admin.web.modules.operator.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 发送跑马灯公告
 * 
 * @author gil
 *
 */
public class ChatNoticeContext {

	/**
	 * 服务器id
	 */
	private List<Integer> serverIds;

	/**
	 * 渠道id
	 */
	private List<String> channels;

	/**
	 * 公告内容
	 */
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
