package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家商城日志
 */
@ApiModel(value = "玩家商城日志")
public class PlayerMarketLogDto {

    /**
     * id
     */
    @ApiModelProperty(value = "日志id，不用显示")
    private String id;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 玩家id
     */
    @ApiModelProperty(value = "玩家id")
    private String playerId;

    /**
     * serverId
     */
    @ApiModelProperty(value = "serverId")
    private int serverId;

    /**
     * 玩家名
     */
    @ApiModelProperty(value = "玩家名")
    private String playerName;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private int level;

    /**
     *  道具ID
     */
    @ApiModelProperty(value = "道具ID")
    private int itemId;

    /**
     * 道具名字
     */
    @ApiModelProperty(value = "道具名字")
    private String itemName;

    /**
     * 物品单价
     */
    @ApiModelProperty(value = "物品单价")
    private int price;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private int count;

    /**
     * 货币类型
     */
    @ApiModelProperty(value = "货币类型")
    private int currency;

    /**
     * 花费金额
     */
    @ApiModelProperty(value = "花费金额")
    private int amount;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private String channelId;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
