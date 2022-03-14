package com.xxgame.logsrv.module.collectlog.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 日志类型
 */
public enum LogType {

    /**测试日志*/
    LOG_DEBUG("LOG_DEBUG"),

    /** 登录日志 */
    LOG_LOGIN("LOG_LOGIN"),

    /** 登出日志 */
    LOG_LOGOUT("LOG_LOGOUT"),

    /** 注册日志*/
    LOG_REGISTER("LOG_REGISTER"),

    /** 玩家创建日志*/
    LOG_CREATER_USER("LOG_CREATER_USER"),

    /**玩家信息日志*/
    LOG_PLAYER_INFO("LOG_PLAYER_INFO"),

    /**玩家每日信息日志*/
    LOG_PLAYER_DAY_INFO("LOG_PLAYER_DAY_INFO"),

    /**充值订单*/
    LOG_ORDER("LOG_ORDER"),

    /**元宝*/
    LOG_GOLD("LOG_GOLD"),

    /**铜币*/
    LOG_MONEY("LOG_MONEY"),

    /** 道具 */
    LOG_ITEM("LOG_ITEM"),

    /** 装备 */
    LOG_EQUIP("LOG_EQUIP"),

    /**GM充值 内币*/
    LOG_GM_GOLD("LOG_GM_GOLD"),

    /** gm操作日志*/
    LOG_GM_OPERATION("LOG_GM_OPERATION"),

    /** 经验 */
    LOG_EXP("LOG_EXP"),

    /** 修为 */
    LOG_REXP("LOG_REXP"),

    /** 真气*/
    LOG_QI("LOG_QI"),

    /**翅膀 */
    LOG_WING("LOG_WING"),

    /** 寻宝 */
    LOG_LOTTERY("LOG_LOTTERY"),

    /** 商城 */
    LOG_MARKET("LOG_MARKET"),

    /** 轮回*/
    LOG_SAMSARA("LOG_SAMSARA"),

    /** 飞剑*/
    LOG_JIAN("LOG_JIAN"),

    /** 充值服支付回调日志 */
    LOG_PAY("LOG_PAY"),

    /** 起服日志 */
    LOG_SRV_INFO("LOG_SRV_INFO"),

    /** 在线统计日志 */
    LOG_ONLINE_STATIS("LOG_ONLINE_STATIS"),

    /** 玩家数据统计 */
    LOG_PLAYER_STATIS("LOG_PLAYER_STATIS"),

    /** 时装 */
    LOG_FASHION("LOG_FASHION"),

    /** 邮件 */
    LOG_MAIL("LOG_MAIL"),

    /** 分享 */
    LOG_SHARE("LOG_SHARE"),

    /** 灵压 */
    LOG_EXPLIOT("LOG_EXPLIOT"),

    /** 成就 */
    LOG_ACHIEVE("LOG_ACHIEVE"),

    /** 符文 */
    LOG_SEALSTONE("LOG_SEALSTONE"),

    /** 离线挂机 */
    LOG_OFFLINE_REWARD("LOG_OFFLINE_REWARD"),

    /** 礼包码 */
    LOG_SERIAL_GIFT("LOG_SERIAL_GIFT"),

    /** 副本 */
    LOG_COPY("LOG_COPY"),

    /** 转生 */
    LOG_REBIRTH("LOG_REBIRTH"),

    /** 仙盟 */
    LOG_FAMILY("LOG_FAMILY"),

    /** 其它货币日志 */
    LOG_OTHER_RESOURCE("LOG_OTHER_RESOURCE"),

    /** 称号日志 */
    LOG_TITLE("LOG_TITLE"),

    /** 神器日志 */
    LOG_CHOP("LOG_CHOP"),

    /** 异闻录日志 */
    LOG_POKEDEX("LOG_POKEDEX"),

    /** 伙伴、圣魂、守护兽日志 */
    LOG_PRACTICE("LOG_PRACTICE"),

    /** 技能日志 */
    LOG_SKILL("LOG_SKILL"),

    /** 技能书日志 */
    LOG_SKILL_BOOK("LOG_SKILL_BOOK"),

    /** 星宿日志 */
    LOG_MERIDIAN("LOG_MERIDIAN"),

    /** 强化 */
    LOG_ENHANCE("LOG_ENHANCE"),

    /** 器灵 */
    LOG_WEAPON_SOUL("LOG_WEAPON_SOUL"),

    /** 仙兽蕴养 */
    LOG_TOTEM("LOG_TOTEM"),

    /** 仙兽灵感 */
    LOG_TOTEM_EXPAND("LOG_TOTEM_EXPAND"),

    /** 击杀boss */
    LOG_BOSS_DEAD("LOG_BOSS_DEAD"),

    /** 混元珠 */
    LOG_HYZ("LOG_HYZ"),

    /** 经验副本领取 */
    LOG_EXP_COPY("LOG_EXP_COPY"),

    /** 战斗力日志 */
    LOG_POWER("LOG_POWER"),
    ;

    /** 日志名 */
    private String logName = "";

    LogType(String name) {
        this.logName = name;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    /**
     * LogType
     * @param logType
     * @return
     */
    public static LogType getByName(String logType) {
        Stream<LogType> stream = Arrays.stream(LogType.values());
        Optional<LogType> optional = stream.filter(value -> StringUtils.equals(value.logName, logType)).findFirst();
        return optional.orElse(null);
    }

}
