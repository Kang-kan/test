package com.xxgame.admin.web.modules.commons.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务器信息
 */
@ApiModel(value = "服务器信息")
public class SrvInfoDto {

    @ApiModelProperty(value = "服务器id")
    private int serverId;

    @ApiModelProperty(value = "开服时间")
    private long openTime;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }
}
