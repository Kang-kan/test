package com.xxgame.admin.web.modules.dailystatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 在线统计
 */
@ApiModel(value = "在线统计")
public class DailyOnlineStatisDto {

    @ApiModelProperty(value = "服务器id")
    private int serverId;

    @ApiModelProperty(value = "渠道id")
    private String channelId;

    @ApiModelProperty(value = "数值格式时间")
    private int dateTime;

    @ApiModelProperty(value = "最高在线")
    private int maxCount;

    @ApiModelProperty(value = "最低在线")
    private int minCount;

    @ApiModelProperty(value = "平均在线")
    private float avgCount;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getDateTime() {
        return dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public float getAvgCount() {
        return avgCount;
    }

    public void setAvgCount(float avgCount) {
        this.avgCount = avgCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
