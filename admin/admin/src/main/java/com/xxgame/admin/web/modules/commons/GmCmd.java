package com.xxgame.admin.web.modules.commons;

/**
 * GM命令
 */
public interface GmCmd {

    int COMMON_QUERY = 1;//通用查询

    int GIFTS_SEND = 2;//添加礼包

    int PLAYERMAIL_SEND = 3;//玩家邮件

    int SYSTEMMAIL_SEND = 4;//系统邮件

    int NOTICE_SEND = 5;//发公告

    int WORDS_BAN = 6;//禁言

    int PLAYER_BAN = 7;//封号

    int KEYWORDS_ADD = 8;//添加禁言关键字

    int WORDS_UNBAN = 9;//解封禁言

    int PLAYER_UNBAN = 10;//解封账号

    int LIST_FAMILY = 11;	// 获取公会列表

    int GET_PLAYER_INFO = 12; // 获取玩家信息

    int LIST_PLAYER_ITEM = 13; // 获取玩家道具信息

    int LIST_PLAYER_EQUIP = 14; // 获取玩家装备信息

    int GET_ONLINE_COUNT = 15; // 获取当前在线人数

    int QUERY_RANK = 16; // 获取排行

    int LIST_AUTO_CLOSE_CHAT = 17; // 自动禁言列表

    int KEYWORDS_DEL = 18;

    int SETSERVER_OPEN_TIME = 19;

    int KICK_PLAYER = 20;

    int CHAT_MONITOR = 22;//聊天监控

    int OPEN_PERFORMANCE = 23;//性能监控开关

    int GM_CHARGE = 24;//gm充值

    int SQL_QUERY = 25;//sql查询

    int SUPERVIP_SET = 26;//超级vip设置

    int AIWEIYOU_GIFT = 27;//爱微游签到分享礼包

    int SUPERVIP_OPEN = 28;//超级vip开关

    int GMCHAT_OPEN = 29;//审发聊天开关

    int SDK_GIFTS_SEND = 30;// SDK礼包  需要验证是否二次领取
    int QUERY_SHAREPLAYER = 31;//获取分享人账号信息
    int REMOVE_GIFT = 32;//删除玩家道具

    /**
     * 查找仙盟信息
     */
    int FIND_FAMILY = 33;

    /**
     * 重新加载策划数据
     */
    int RELOAD_DATA = 888;

    /**
     * 分页查找玩家列表
     */
    int PLAYER_LIST = 34;

    /**
     * 查找玩家id
     */
    int QUERY_PLAYER_ID = 35;

    /**
     * 修改仙盟公告
     */
    int UPDATE_FAMILY_NOTICE = 36;

    /**
     * 充值金额排名统计
     */
    int CHARGE_RANK = 37;

    /**
     * 同步屏蔽字库
     */
    int SYNC_BLOCK_WORDS = 38;

    /**
     * 充值
     */
    int CHARGEIDENTITY =  1000;

}
