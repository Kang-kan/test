package com.xxgame.admin.web.modules.consumestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 元宝消耗统计
 */
@ApiModel(value = "元宝消耗统计")
public class GoldConsumeStatisDto {

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private int dateTime;

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 总人数
     */
    @ApiModelProperty(value = "总人数")
    private int total;

    /**
     * 行为
     */
    @ApiModelProperty(value = "行为")
    private String functionType;

    /**
     * 花费人数
     */
    @ApiModelProperty(value = "花费人数")
    private int functionCount;

    /**
     * 消耗元宝
     */
    @ApiModelProperty(value = "消耗元宝")
    private int gold;

    public int getDateTime() {
        return dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public int getFunctionCount() {
        return functionCount;
    }

    public void setFunctionCount(int functionCount) {
        this.functionCount = functionCount;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
