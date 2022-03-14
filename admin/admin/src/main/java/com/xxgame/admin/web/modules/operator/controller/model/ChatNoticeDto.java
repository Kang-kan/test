package com.xxgame.admin.web.modules.operator.controller.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 跑马灯公告DTO
 */
@ApiModel(value = "跑马灯公告DTO")
public class ChatNoticeDto {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    /**
     * 服务器id列表
     */
    @ApiModelProperty(value = "服务器id列表，为空或空列表，表示全服")
    private List<Integer> serverIds;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id，为空或空列表，表示全渠道")
    private List<String> channels;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String notice;

    /**
     * 时间间隔
     */
    @ApiModelProperty(value = "时间间隔，单位秒")
    private int intervalTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private long updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
