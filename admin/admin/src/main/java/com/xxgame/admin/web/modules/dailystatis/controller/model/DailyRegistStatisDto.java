package com.xxgame.admin.web.modules.dailystatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 注册统计
 */
@ApiModel(value = "注册统计")
public class DailyRegistStatisDto {

    @ApiModelProperty(value = "数值格式时间")
    private int dateTime;

    @ApiModelProperty(value = "渠道")
    private String channelId;

    @ApiModelProperty(value = "服务器id")
    private int serverId;

    @ApiModelProperty(value = "注册人数")
    private int count;

    public int getDateTime() {
        return dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
