package com.xxgame.admin.web.modules.gamelog.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 玩家邮件日志
 */
@ApiModel(value = "玩家邮件日志")
public class PlayerMailLogDto extends BasePlayerLogDto {

    /**
     * 邮件id
     */
    @ApiModelProperty(value = "邮件id")
    private String mailId;

    /**邮件创建时间 */
    @ApiModelProperty(value = "邮件创建时间")
    private long createTime;

    /**邮件标题*/
    @ApiModelProperty(value = "标题")
    private String title;

    /**邮件内容*/
    @ApiModelProperty(value = "内容")
    private String message;

    /**邮件礼包内容*/
    @ApiModelProperty(value = "附件内容")
    private String bonus;

    /**邮件是否以读(0表示未读 1表示已读)*/
    @ApiModelProperty(value = "是否以读(0表示未读 1表示已读)")
    private int readStatus;

    /**邮件是否已经提取附件*/
    @ApiModelProperty(value = "否已经提取附件,1-已领取")
    private int isGet;

    /**邮件配置表id mailSetting.xml*/
    @ApiModelProperty(value = "邮件配置表id")
    private int configId;

    /**
     * 操作，-1-未读取，0-领取，1-手动删除，2-系统删除，3-读取
     */
    @ApiModelProperty(value = "操作，-1-未读取，0-领取，1-手动删除，2-系统删除，3-读取")
    private int opValue;

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public int getIsGet() {
        return isGet;
    }

    public void setIsGet(int isGet) {
        this.isGet = isGet;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getOpValue() {
        return opValue;
    }

    public void setOpValue(int opValue) {
        this.opValue = opValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
