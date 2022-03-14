package com.xxgame.admin.web.modules.levelstatis.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 等级人数
 */
@ApiModel(value = "等级人数")
public class LevelCount {

    @ApiModelProperty(value = "等级")
    private int level;

    @ApiModelProperty(value = "人数")
    private int count;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
