package com.xxgame.admin.web.modules.chargestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 单笔充值分布
 */
@ApiModel(value = "单笔充值分布")
public class SingleChargeDistDto {

    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道")
    private String channelId;

    /**
     * 单笔金额
     */
    @ApiModelProperty(value = "单笔金额")
    private int amount;

    /**
     * 充值次数
     */
    @ApiModelProperty(value = "充值次数")
    private int count;

    /**
     * 总充值金额
     */
    @ApiModelProperty(value = "总充值金额")
    private long totalAmount;

    /**
     * 总充值次数
     */
    @ApiModelProperty(value = "总充值次数")
    private int totalCount;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
