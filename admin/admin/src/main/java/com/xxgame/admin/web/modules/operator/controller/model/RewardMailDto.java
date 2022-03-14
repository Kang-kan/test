package com.xxgame.admin.web.modules.operator.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 奖励邮件DTO
 */
@ApiModel(value = "奖励邮件DTO")
public class RewardMailDto {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String creator;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 类型，1-全服邮件，2-个人邮件
     */
    @ApiModelProperty(value = "类型，1-全服邮件，2-个人邮件")
    private int type;

    /**
     * 服务器id列表
     */
    @ApiModelProperty(value = "服务器id列表")
    private List<Integer> serverIds;

    /**
     * 玩家列表
     */
    @ApiModelProperty(value = "玩家列表")
    private List<MailPlayerInfoDto> players;

    /**
     * 邮件标题
     */
    @ApiModelProperty(value = "邮件标题")
    private String title;

    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容")
    private String content;

    /**
     * 奖励
     */
    @ApiModelProperty(value = "奖励")
    private String rewards;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private long startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private long endTime;

    /**
     * 失败的服务器id列表
     */
    @ApiModelProperty(value = "失败的服务器id列表")
    private String failSrvIds;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private String reviewer;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private long reviewTime;

    /**
     * 发送者
     */
    @ApiModelProperty(value = "发送者")
    private String sender;

    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private long sendTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getFailSrvIds() {
        return failSrvIds;
    }

    public void setFailSrvIds(String failSrvIds) {
        this.failSrvIds = failSrvIds;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public long getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(long reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
