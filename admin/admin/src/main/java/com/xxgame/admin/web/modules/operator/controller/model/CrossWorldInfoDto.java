package com.xxgame.admin.web.modules.operator.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * 世界服信息DTO
 */
@ApiModel(value = "世界服信息DTO")
public class CrossWorldInfoDto {

    @ApiModelProperty(value = "世界服配置，{ worldId : GameNodeInfoDto }")
    private Map<Integer, CrossWorldSrvDto> worldServers;

    public Map<Integer, CrossWorldSrvDto> getWorldServers() {
        return worldServers;
    }

    public void setWorldServers(Map<Integer, CrossWorldSrvDto> worldServers) {
        this.worldServers = worldServers;
    }

}
