package com.xxgame.admin.web.modules.customerservice.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 玩家道具
 */
@ApiModel(value = "玩家道具")
public class PlayerItemDto {

    /**
     * 道具ID
     */
    @ApiModelProperty(value = "道具ID")
    private int itemId;

    /**
     * 道具的数量
     */
    @ApiModelProperty(value = "道具的数量")
    private int itemCount;

    /**
     * 道具失效时间
     */
    @ApiModelProperty(value = "道具失效时间，>0才显示到期时间")
    private long endTime;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
