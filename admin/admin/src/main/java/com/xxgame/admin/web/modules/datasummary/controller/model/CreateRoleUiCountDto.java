package com.xxgame.admin.web.modules.datasummary.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 到达创角界面人数DTO
 */
@ApiModel(value = "到达创角界面人数DTO")
public class CreateRoleUiCountDto {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String channelId;

    /**
     * 人数
     */
    @ApiModelProperty(value = "人数")
    private int count;

    /**
     * 加载人数
     */
    @ApiModelProperty(value = "加载人数")
    private int loadingCount;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLoadingCount() {
        return loadingCount;
    }

    public void setLoadingCount(int loadingCount) {
        this.loadingCount = loadingCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
