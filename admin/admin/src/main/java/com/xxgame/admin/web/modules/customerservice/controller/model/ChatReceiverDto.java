package com.xxgame.admin.web.modules.customerservice.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 聊天接收方
 */
@ApiModel(value = "聊天接收方")
public class ChatReceiverDto {

    /**
     * 类型：5-私聊，7-好友
     */
    @ApiModelProperty(value = "类型：5-私聊，7-好友")
    private int type;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private long id;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字")
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
