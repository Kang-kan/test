package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家道具日志
 */
@ApiModel(value = "玩家道具日志")
public class PlayerItemLogDto extends BasePlayerLogDto {

    /**
     * 物品类型
     */
    @ApiModelProperty(value = "物品类型")
    private String bonusType;

    /**
     * 物品id
     */
    @ApiModelProperty(value = "物品id")
    private int itemId;

    /**
     * 物品名字
     */
    @ApiModelProperty(value = "物品名字")
    private String itemName;

    /**
     * 增减数量
     */
    @ApiModelProperty(value = "增减数量")
    private long count;

    /**
     * 当前数量
     */
    @ApiModelProperty(value = "当前数量")
    private long currentCount;

    /**
     * 变化类型
     */
    @ApiModelProperty(value = "变化类型")
    private String functionType;

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(long currentCount) {
        this.currentCount = currentCount;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
