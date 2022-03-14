package com.xxgame.admin.web.modules.consumestatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商城消耗统计
 */
@ApiModel(value = "商城消耗统计")
public class MarketConsumeStatisDto {

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private long dateTime;

    /**
     * 服务器id
     */
    @ApiModelProperty(value = "服务器id")
    private int serverId;

    /**
     * 购买总人数
     */
    @ApiModelProperty(value = "购买总人数")
    private int totalPlayerCount;

    /**
     * 购买物品总次数
     */
    @ApiModelProperty(value = "购买物品总次数")
    private int totalTimes;

    /**
     * 购买总额
     */
    @ApiModelProperty(value = "购买总额")
    private int totalAmount;

    /**
     * 物品id
     */
    @ApiModelProperty(value = "物品id")
    private int itemId;

    /**
     * 购物物品数据
     */
    @ApiModelProperty(value = "购物物品数据")
    private int itemCount;

    /**
     * 购物物品名
     */
    @ApiModelProperty(value = "购物物品名")
    private String itemName;

    /**
     * 人数
     */
    @ApiModelProperty(value = "人数")
    private int playerCount;

    /**
     * 购买物品次数
     */
    @ApiModelProperty(value = "购买物品次数")
    private int times;

    /**
     * 货币类型
     */
    @ApiModelProperty(value = "货币类型，1-元宝 2-积分")
    private int currency;

    /**
     * 花费金额
     */
    @ApiModelProperty(value = "花费金额")
    private int amount;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getTotalPlayerCount() {
        return totalPlayerCount;
    }

    public void setTotalPlayerCount(int totalPlayerCount) {
        this.totalPlayerCount = totalPlayerCount;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(int totalTimes) {
        this.totalTimes = totalTimes;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
