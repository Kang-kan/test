package com.xxgame.admin.web.modules.operator.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "后台节点信息DTO")
public class NodeInfoDto {

    @ApiModelProperty(value = "节点")
    private int node;

    @ApiModelProperty(value = "服务器id列表")
    private List<Integer> serverIds = new ArrayList<>();

    @ApiModelProperty(value = "基准时间")
    private long baseTime;

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public List<Integer> getServerIds() {
        return serverIds;
    }

    public void setServerIds(List<Integer> serverIds) {
        this.serverIds = serverIds;
    }

    public long getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(long baseTime) {
        this.baseTime = baseTime;
    }
}
