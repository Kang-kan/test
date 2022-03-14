package com.xxgame.admin.web.modules.operator.model;

/**
 * 邮件额外参数
 */
public class MailExtraParam {

    /** 玩家最小等级 */
    private Integer playerLevelStart = 0;
    /** 玩家最大等级 */
    private Integer playerLevelEnd = 999;
    /** 转生最小等级 */
    private Integer rebirthLevelStart = 0;
    /** 转生最大等级 */
    private Integer rebirthLevelEnd = 999;
    /** VIP最小等级 */
    private Integer vipLevelStart = 0;
    /** VIP最大等级 */
    private Integer vipLevelEnd = 999;

    private Integer operator = 0;

    public Integer getPlayerLevelStart() {
        return playerLevelStart;
    }

    public void setPlayerLevelStart(Integer playerLevelStart) {
        this.playerLevelStart = playerLevelStart;
    }

    public Integer getPlayerLevelEnd() {
        return playerLevelEnd;
    }

    public void setPlayerLevelEnd(Integer playerLevelEnd) {
        this.playerLevelEnd = playerLevelEnd;
    }

    public Integer getRebirthLevelStart() {
        return rebirthLevelStart;
    }

    public void setRebirthLevelStart(Integer rebirthLevelStart) {
        this.rebirthLevelStart = rebirthLevelStart;
    }

    public Integer getRebirthLevelEnd() {
        return rebirthLevelEnd;
    }

    public void setRebirthLevelEnd(Integer rebirthLevelEnd) {
        this.rebirthLevelEnd = rebirthLevelEnd;
    }

    public Integer getVipLevelStart() {
        return vipLevelStart;
    }

    public void setVipLevelStart(Integer vipLevelStart) {
        this.vipLevelStart = vipLevelStart;
    }

    public Integer getVipLevelEnd() {
        return vipLevelEnd;
    }

    public void setVipLevelEnd(Integer vipLevelEnd) {
        this.vipLevelEnd = vipLevelEnd;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }
}
