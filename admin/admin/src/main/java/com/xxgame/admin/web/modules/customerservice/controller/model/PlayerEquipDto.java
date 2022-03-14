package com.xxgame.admin.web.modules.customerservice.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 玩家装备
 */
@ApiModel(value = "玩家装备")
public class PlayerEquipDto {

    /**
     * 装备ID
     */
    @ApiModelProperty(value = "装备ID")
    private int equipId;

    /**
     * 0-未上身, 1-上身
     */
    @ApiModelProperty(value = "0-未上身, 1-上身")
    private int wear;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private int count;

    public int getEquipId() {
        return equipId;
    }

    public void setEquipId(int equipId) {
        this.equipId = equipId;
    }

    public int getWear() {
        return wear;
    }

    public void setWear(int wear) {
        this.wear = wear;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
