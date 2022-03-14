package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家器灵日志
 */
@ApiModel(value = "玩家器灵日志")
public class PlayerWeaponSoulLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 操作，0-穿戴，1-升级，2-熔炼，3-合成
     */
    @ApiModelProperty(value = "操作，0-穿戴，1-升级，2-熔炼，3-合成")
    private int opValue;

    /**
     * 槽位
     */
    @ApiModelProperty(value = "槽位")
    private int weaponIndex;

    /**
     * 装备id
     */
    @ApiModelProperty(value = "装备id")
    private int equipId;

    /**
     * 消耗
     */
    @ApiModelProperty(value = "消耗")
    private String cost;

    /**
     * 增加熔炼值
     */
    @ApiModelProperty(value = "增加熔炼值")
    private int elfExp;

    /**
     * 熔炼的装备id
     */
    @ApiModelProperty(value = "熔炼旧的装备id")
    private String deleteEquipIds;

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

    public int getWeaponIndex() {
        return weaponIndex;
    }

    public void setWeaponIndex(int weaponIndex) {
        this.weaponIndex = weaponIndex;
    }

    public int getEquipId() {
        return equipId;
    }

    public void setEquipId(int equipId) {
        this.equipId = equipId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getElfExp() {
        return elfExp;
    }

    public void setElfExp(int elfExp) {
        this.elfExp = elfExp;
    }

    public String getDeleteEquipIds() {
        return deleteEquipIds;
    }

    public void setDeleteEquipIds(String deleteEquipIds) {
        this.deleteEquipIds = deleteEquipIds;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
