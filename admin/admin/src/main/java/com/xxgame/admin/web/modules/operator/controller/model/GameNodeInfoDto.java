package com.xxgame.admin.web.modules.operator.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 跨服配置中心服DTO
 */
@ApiModel(value = "跨服配置中心服DTO")
public class GameNodeInfoDto {

    @ApiModelProperty(value = "游戏服id")
    private int serverId;

    @ApiModelProperty(value = "世界服id")
    private int worldId;

    @ApiModelProperty(value = "世界服名")
    private String worldName;

    @ApiModelProperty(value = "节点")
    private int node;

    @ApiModelProperty(value = "节点基准时间")
    private long baseTime;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getWorldId() {
        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public long getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(long baseTime) {
        this.baseTime = baseTime;
    }
}
