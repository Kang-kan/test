package com.xxgame.admin.web.modules.datasummary.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 单服数据简表
 */
@ApiModel(value = "单服数据简表")
public class SingleSrvSimpleDto {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private int daily;

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 开服天数
     */
    @ApiModelProperty(value = "开服天数")
    private int openDay;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private int amount;

    /**
     * 最高在线
     */
    @ApiModelProperty(value = "最高在线")
    private int maxOnline;

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getOpenDay() {
        return openDay;
    }

    public void setOpenDay(int openDay) {
        this.openDay = openDay;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxOnline() {
        return maxOnline;
    }

    public void setMaxOnline(int maxOnline) {
        this.maxOnline = maxOnline;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
