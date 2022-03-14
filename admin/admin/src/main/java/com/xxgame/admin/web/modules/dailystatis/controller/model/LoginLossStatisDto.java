package com.xxgame.admin.web.modules.dailystatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 登录留存
 */
@ApiModel(value = "登录留存")
public class LoginLossStatisDto {

    @ApiModelProperty(value = "开服时间-毫秒")
    private long openTime;

    @ApiModelProperty(value = "渠道")
    private String channelId;

    @ApiModelProperty(value = "服务器id")
    private int serverId;

    @ApiModelProperty(value = "当天注册")
    private int firstCreate;

    @ApiModelProperty(value = "留存")
    private List<ActiveLoss> activeLoss;

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
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

    public int getFirstCreate() {
        return firstCreate;
    }

    public void setFirstCreate(int firstCreate) {
        this.firstCreate = firstCreate;
    }

    public List<ActiveLoss> getActiveLoss() {
        return activeLoss;
    }

    public void setActiveLoss(List<ActiveLoss> activeLoss) {
        this.activeLoss = activeLoss;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
