package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家符文日志
 */
@ApiModel(value = "玩家符文日志")
public class PlayerSealStoneLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 操作，0-获得，1-装备，2-升级，3-替换，4-分解
     */
    @ApiModelProperty(value = "操作，0-获得，1-装备，2-升级，3-替换，4-分解")
    private int opValue;

    /**装备部位*/
    @ApiModelProperty(value = "装备部位，-1未装备")
    private int equipIndex;

    /**装备ID*/
    @ApiModelProperty(value = "装备ID")
    private int equipId;

    public int getHeroConfigId() {
        return heroConfigId;
    }

    public void setHeroConfigId(int heroConfigId) {
        this.heroConfigId = heroConfigId;
    }

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    public int getEquipIndex() {
        return equipIndex;
    }

    public void setEquipIndex(int equipIndex) {
        this.equipIndex = equipIndex;
    }

    public int getEquipId() {
        return equipId;
    }

    public void setEquipId(int equipId) {
        this.equipId = equipId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
