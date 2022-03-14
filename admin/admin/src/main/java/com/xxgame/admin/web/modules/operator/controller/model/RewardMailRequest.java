package com.xxgame.admin.web.modules.operator.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 奖励邮件请求对象
 * 
 * @author gil
 *
 */
@ApiModel(value = "奖励邮件请求对象")
public class RewardMailRequest {

	/**
	 * 类型，1-全服邮件，2-个人邮件
	 */
	@ApiModelProperty(value = "类型，1-全服邮件，2-个人邮件")
	private int type;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注，长度 <= 32")
	private String remark;

	/**
	 * 服务器id
	 */
	@ApiModelProperty(value = "服务器id")
	private List<Integer> serverIds;

	/**
	 * 玩家列表
	 */
	@ApiModelProperty(value = "玩家列表")
	private List<MailPlayerInfoDto> players;

	/**
	 * 邮件标题
	 */
	@ApiModelProperty(value = "邮件标题，长度 <= 128")
	private String title;

	/**
	 * 邮件内容
	 */
	@ApiModelProperty(value = "邮件内容，长度 <= 1024")
	private String content;

	/**
	 * 奖励
	 */
	@ApiModelProperty(value = "奖励，奖励格式[{'id':xx, 'num':xx}]，长度 <= 1024")
	private String rewards;

	/**
	 * 开始时间
	 */
	private long startTime;

	/**
	 * 结束时间
	 */
	private long endTime;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Integer> getServerIds() {
		return serverIds;
	}

	public void setServerIds(List<Integer> serverIds) {
		this.serverIds = serverIds;
	}

	public List<MailPlayerInfoDto> getPlayers() {
		return players;
	}

	public void setPlayers(List<MailPlayerInfoDto> players) {
		this.players = players;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
