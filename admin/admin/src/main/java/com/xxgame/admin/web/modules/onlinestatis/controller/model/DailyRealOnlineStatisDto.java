package com.xxgame.admin.web.modules.onlinestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "每日实时在线统计DTO")
public class DailyRealOnlineStatisDto {

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private int daily;

    /**
     * 每小时在线数据
     */
    @ApiModelProperty(value = "每小时在线数据")
    private List<Integer> statis;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public List<Integer> getStatis() {
        return statis;
    }

    public void setStatis(List<Integer> statis) {
        this.statis = statis;
    }
}
