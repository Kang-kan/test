package com.xxgame.admin.web.modules.dailystatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 登录留存
 */
@ApiModel(value = "登录留存V1DTO")
public class LoginLossStatisV1Dto {

    @ApiModelProperty(value = "日期")
    private int daily;

    @ApiModelProperty(value = "渠道")
    private String channelId;

    @ApiModelProperty(value = "服务器id")
    private int serverId;

    @ApiModelProperty(value = "注册人数")
    private int createCount;

    @ApiModelProperty(value = "留存")
    private List<ActiveLossV1> activeLoss;

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
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

    public int getCreateCount() {
        return createCount;
    }

    public void setCreateCount(int createCount) {
        this.createCount = createCount;
    }

    public List<ActiveLossV1> getActiveLoss() {
        return activeLoss;
    }

    public void setActiveLoss(List<ActiveLossV1> activeLoss) {
        this.activeLoss = activeLoss;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
