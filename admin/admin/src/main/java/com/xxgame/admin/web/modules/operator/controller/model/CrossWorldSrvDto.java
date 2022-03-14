package com.xxgame.admin.web.modules.operator.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 世界服配置DTO
 */
@ApiModel(value = "世界服配置DTO")
public class CrossWorldSrvDto {

    @ApiModelProperty(value = "世界服id")
    private int worldId;

    @ApiModelProperty(value = "socket地址")
    private String socketHost;

    @ApiModelProperty(value = "socket端口")
    private int socketPort;

    @ApiModelProperty(value = "web地址")
    private String webHost;

    @ApiModelProperty(value = "web端口")
    private int webPort;

    @ApiModelProperty(value = "开始节点")
    private int beginNode;

    @ApiModelProperty(value = "结束节点")
    private int endNode;

    @ApiModelProperty(value = "名字")
    private String name;

    public int getWorldId() {
        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public String getSocketHost() {
        return socketHost;
    }

    public void setSocketHost(String socketHost) {
        this.socketHost = socketHost;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        this.webHost = webHost;
    }

    public int getWebPort() {
        return webPort;
    }

    public void setWebPort(int webPort) {
        this.webPort = webPort;
    }

    public int getBeginNode() {
        return beginNode;
    }

    public void setBeginNode(int beginNode) {
        this.beginNode = beginNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
