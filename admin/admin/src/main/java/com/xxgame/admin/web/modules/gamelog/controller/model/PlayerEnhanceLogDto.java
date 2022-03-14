package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家强化日志
 */
@ApiModel(value = "玩家强化日志")
public class PlayerEnhanceLogDto extends BasePlayerLogDto {

    /**
     * 英雄配置id
     */
    @ApiModelProperty(value = "英雄配置id")
    private int heroConfigId;

    /**
     * 强化类型，0-强化，1-精练，2-灵石
     */
    @ApiModelProperty(value = "强化类型，0-强化，1-精练，2-灵石")
    private int type;

    /**
     * 槽位
     */
    @ApiModelProperty(value = "槽位")
    private int enIndex;

    /**
     * 强化等级
     */
    @ApiModelProperty(value = "强化等级")
    private int enLevel;

    /**
     * 消耗
     */
    @ApiModelProperty(value = "消耗")
    private String cost;

    public int getHeroConfigId() {
        return heroConfigId;
    }

    public void setHeroConfigId(int heroConfigId) {
        this.heroConfigId = heroConfigId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEnIndex() {
        return enIndex;
    }

    public void setEnIndex(int enIndex) {
        this.enIndex = enIndex;
    }

    public int getEnLevel() {
        return enLevel;
    }

    public void setEnLevel(int enLevel) {
        this.enLevel = enLevel;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
