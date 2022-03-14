const api = {
  Login: '/auth/login',
  Logout: '/auth/logout',
  ForgePassword: '/auth/forge-password',
  Register: '/auth/register',
  twoStepCode: '/auth/2step-code',
  SendSms: '/systemuser/sms',
  SendSmsErr: '/account/sms_err',
  // get my info
  UserInfo: '/user/info',
  // 相关接口
  Test: '/demo/test',
  // ==========================角色相关===========================
  /**
   * 添加角色
   */
  AddRole: 'systemRole/add',
  /**
   * 删除角色
   */
  DeleteRole: 'systemRole/delete',
  /**
   * 更新角色
   */
  UpdateRole: 'systemRole/update',
  /**
   * 根据角色id获取角色信息
   */
  GetRoleById: 'systemRole/getRole',
  /**
   * 获取角色列表
   */
  GetRoleList: 'systemRole/roleList',
  // ============================角色权限=====================================
  /**
   * 删除角色权限
   */
  DeleteRoleRight: 'systemRoleRight/delete',
  /**
   * 获取角色权限
   */
  GetRoleRights: 'systemRoleRight/getRights',
  /**
   * 更新角色权限
   */
  UpdateRight: 'systemRoleRight/updateRight',
  // ====================================角色用户关系=========================================
  /**
   * 添加用户角色信息
   */
  AddUserRole: 'systemUserRole/add',
  /**
   * 删除用户角色
   */
  DeleteUserRole: 'systemUserRole/delete',
  /**
   * 获取本用户角色信息
   */
  GetMyroles: 'systemUserRole/getMyRoles',
  /**
   * 根据用户id获取用户角色信息
   */
  GetRolesByUser: 'systemUserRole/getRolesByUser',

  
  /**
   * 根据角色id获取用户角色信息
   */
  GetUsersByRole: 'systemUserRole/getUsersByRole',
  /**
   * 每日汇总
   */
  DailySummary: '/dataSummary/dailySummarys',
  /**
   * 经验副本统计
   */
  ExpCopyStatis: '/dataSummary/expCopyStatis',
  /**
   * 小时汇总
   */
  HourSummary: '/dataSummary/hourSummarys',
  /**
   * 月活跃汇总
   */
  MonthylyActive: '/dataSummary/monthlyActives',
  /**
   * 区服充值汇总
   */
  ServerCharge: '/dataSummary/serverCharge',
  /**
   * 各服时间段充值
   */
  ServerListCharge: '/dataSummary/serverListCharges',
  /**
   * 到达创角界面人数
   */
  CreateRoleUiCounter: '/dataSummary/createRoleUiCounter',
  /**
   * 单服数据简表
   */
  SingleSrv: '/dataSummary/singleSrvs',
  /**
   * 当日汇总
   */
  TodaySummary: '/dataSummary/todaySummarys',
  /**
   * 充值统计
   */
  ChargeStatics: '/dailystatis/chargeStatis',
  /**
   * 注册统计
   */
  RegisterStatis: '/dailystatis/registStatiss',
  /**
   * 在线统计
   */
  DailyOnlineStatis: '/dailystatis/onlineStatiss',
  /**
   * 登录留存
   */
  LoginRetain: 'dailystatis/loginRetains',
  /**
   * 实时在线统计
   */
  RealTimeOnline: '/onlinestatis/realTimeOnlines',
  /**
   * 在线时长分布
   */
  OnlineTimeDist: '/onlinestatis/onlineTimeDist',
  /**
   * 商城消耗统计
   */
  MarkConsumeStatis: '/consumestatis/markConsumeStatis',
  /**
   * 仙晶消耗统计
   */
  GoldFunctionStatis: '/consumestatis/goldFunctionStatis',
  /**
   * 等级流失统计
   */
  LevelLoss: '/levelstatis/levelloss1',
  /**
   * 等级分布统计
   */
  LevelDist: '/levelstatis/leveldist',
  /**
   * 单笔充值分布
   */
  ChargeSingleDist: '/chargestatis/chargeSingleDist',
  /**
   * 单笔充值分布V1全渠道
   */
  chargeSingleDistsV1: '/chargestatis/chargeSingleDistsV1',
  /**
   * 单笔充值分布V1
   */
  chargeSingleDistV1: '/chargestatis/chargeSingleDistV1',
  /**
   * 首充等级统计
   */
  ChargeFirstLevelDist: '/chargestatis/chargeFirstLevelDist',
  /**
   * 付费等级分布
   */
  ChargeLevelDist: '/chargestatis/chargeLevelDist',
  /**
   * 付费登录流失
   */
  ChargeLoginLoss: '/chargestatis/chargeLoginLosss',
  /**
   * 累计充值分布
   */
  ChargeTotalDist: '/chargestatis/chargeTotalDist',
  /**
   * 仙晶消耗排行
   */
  CostGoldRank: '/chargestatis/costGoldRank',
  /**
   * 月充值汇总
   */
  ChargeMonthSummary: '/chargestatis/chargeMonthSummarys',
  /**
   * 根据定单id查找充值定单
   */
  FindById: '/chargeOrder/findById',
  /**
   * 根据玩家id查找充值定单
   */
  FindByPlayerId: '/chargeOrder/findByPlayerId',
  /**
   * 单条客服反馈数据查询
   */
  SingleCustomerService: '/cs/findByPlayer',
  /**
   * 客服反馈列表数据查询
   */
  CustomerService: 'cs/listCsMessage',
  /**
   * 客服列表回复
   */
  PostCustomerService: 'cs/replyMessage',
  /**
   * 获取服务器信息
   */
  GetSrvInfo: '/common/getSrvInfo',
  /**
   * 查询玩家登录日志
   */
  QueryPlayerLoginLog: '/gamelog/queryPlayerLoginLog',
  /**
   * 查询玩家登出日志
   */
  QueryPlayerLogoutLog: '/gamelog/queryPlayerLogoutLog',
  /**
   * 查询玩家仙晶日志
   */
  QueryPlayerGoldLog: '/gamelog/queryPlayerGoldLog1',
  /**
   * 查询玩家金币日志
   */
  QueryPlayerMoneyLog: '/gamelog/queryPlayerMoneyLog1',
  /**
   * 查询玩家道具日志
   */
  QueryPlayerItemLog: '/gamelog/queryPlayerItemLog1',
  /**
   * 查询玩家商城日志
   */
  QueryPlayerMarketLogLog: '/gamelog/queryPlayerMarketLog1',
  /**
   * 根据道具id商城日志
   */
  QueryMarketLogLogById: '/gamelog/queryMarketLogByItem1',
  /**
   * 查询玩家充值日志
   */
  QueryPlayerChargeLog: '/gamelog/queryPlayerChargeLog',
  /**
   * 导出充值账单
   */
  ExportChargeBill: '/gamelog/exportChargeBill',
  /**
   * 查询全服充值日志
   */
  QueryAllChargeLog: '/gamelog/queryPlayerChargeLogs',
  /**
   * 根据游戏定单id查找充值日志
   */
  QueryPlayerChargeLogById: '/gamelog/queryPlayerChargeLogById',
  /**
   * 查询玩家经验日志
   */
  QueryPlayerExpLog: '/gamelog/queryPlayerExpLog',
  /**
   * 查询玩家修为日志
   */
  QueryPlayerRExpLog: '/gamelog/queryPlayerRExpLog',
  /**
   * 查询玩家时装日志
   */
  QueryPlayerFashionLog: '/gamelog/queryPlayerFashionLog',
  /**
   * 查询玩家邮件日志
   */
  QueryPlayerMailLog: '/gamelog/queryPlayerMailLog1',
  /**
   * 根据服务器id邮件标题分页查找邮件日志
   */
  QueryMailLogByTitle: '/gamelog/findMailLogBySrvTitle',
  /**
   * 查询玩家分享日志
   */
  QueryPlayerShareLog: '/gamelog/queryPlayerShareLog1',
  /**
   * 查询玩家灵压日志
   */
  QueryPlayerExpliotLog: '/gamelog/queryPlayerExpliotLog',
  /**
   * 查询玩家符文日志
   */
  QueryPlayerSealStoneLog: '/gamelog/queryPlayerSealStoneLog1',
  /**
   * 查询玩家离线日志
   */
  QueryPlayerOfflineRewardLog: '/gamelog/queryPlayerOfflineRewardLog',
  /**
   * 查询玩家礼包码日志
   */
  QueryPlayerSerialGiftLog: '/gamelog/queryPlayerSerialGiftLog',
  /**
   * 根据礼包码查询日志
   */
  QuerySerialGiftLog: '/gamelog/querySerialGiftLog',
    /**
   * 根据副本查询日志
   */
  QueryCopyLog: '/gamelog/queryPlayerCopyLog1',
    /**
   * 根据转生查询日志
   */
  QueryRebirthLog: '/gamelog/queryPlayerRebirthLog',
  /**
   * @description 仙盟日志
   */
  QueryFamilyLog: '/gamelog/queryFamilyLog',
    /**
   * @description 根据id或名字查询仙盟日志
   */
  QueryFamilyLogById: '/gamelog/queryFamilyLogByIdOrName',
  /**
   * 其他货币日志
   */
  QueryOtherResourceLog: '/gamelog/queryOtherResourceLog1',
  /**
   * 成就日志
   */
  QueryPlayerAchieveLog: '/gamelog/queryPlayerAchieveLog1',
  /**
   * 称号日志
   */
  QueryPlayerTitleLog: '/gamelog/queryPlayerTitleLog1',
  /**
   * 神器日志
   */
  QueryPlayerChopLog: '/gamelog/queryPlayerChopLog',
  /**
   * @description 异闻录日志
   */
  QueryPlayerPokedexLog: '/gamelog/queryPlayerPokedexLog1',
  /**
   * 圣魂日志
   */
  QueryPlayerPracticeLog: '/gamelog/queryPlayerPracticeLog',
  /**
   * 玩家技能日志
   */
  QueryPlayerSkillLog: '/gamelog/queryPlayerSkillLog',
  /**
   * 心法技能日志
   */
  QueryPlayerSkillBookLog: '/gamelog/queryPlayerSkillBookLog',
  /**
   * 星宿日志
   */
  QueryPlayerMeridianLog: '/gamelog/queryPlayerMeridianLog',
  /**
   * 强化日志
   */
  QueryPlayerEnhanceLog: '/gamelog/queryPlayerEnhanceLog1',
  /**
   * 器灵日志
   */
  QueryPlayerWeaponSoulLog: '/gamelog/queryPlayerWeaponSoulLog',
  /**
   * 仙兽蕴养日志
   */
  QueryPlayerTotemLog: '/gamelog/queryPlayerTotemLog',
  /**
   * 仙兽灵感日志
   */
  QueryPlayerTotemExpandLog: '/gamelog/queryPlayerTotemExpandLog',
  /**
   * 根据战斗id查找boss击杀日志
   */
  QueryBossDeadLogByBattleId: '/gamelog/queryBossDeadLogByBattleId',
  /**
   * 根据服务器id、玩法类型查找boss击杀日志
   */
  QueryBossDeadLogBySrvAndType: '/gamelog/queryBossDeadLogBySrvAndType',
  /** 
   * 查询玩家boss击杀日志
  */
  QueryPlayerBossDeadLog: 'gamelog/queryPlayerBossDeadLog',
  //==============
  /** 
   * 生成礼包码
  */
 CreateGift: 'giftManager/createGift',
 /** 
   * 查询可用礼包码
  */
 QueryGift: 'giftManager/queryGift',
 /** 
   * 查询指定礼包码
  */
 QueryGiftCode: 'giftManager/queryGiftCode',
 /** 
   * 生成礼包
  */
 CreateGiftRewards: 'giftManager/createGiftReward',
 /** 
   * 查询所有礼包
  */
 QueryGiftRewards: 'giftManager/getGiftRewards',
 /** 
   * 修改礼包
  */
 UpdataGiftRewards: 'giftManager/updateGiftReward',
 /** 
   * 仙盟列表
  */
 ListFamily: 'familyManager/listFamily',
 /** 
   * 修改仙盟公告
  */
 UpdateFamily: 'familyManager/updateFamilyNotice',
 /** 
   * 角色列表
  */
 PlayerList: 'playerManager/playerList',
 /** 
   * 查询玩家基本信息
  */
 QueryPlayerInfos: 'playerManager/queryPlayerInfos',
 /** 
   *
  */
 BlockPlayer: 'playerManager/blockPlayer',
 /** 
   * 禁言
  */
 BlockChat: 'playerManager/blockChat',

  /** 
   * 查询玩家禁言列表
  */
 QueryPlayerBlockChat: '/playerManager/queryBlockChat',
  /** 
   * 查询全部禁言列表
  */
  QueryBlockChats: '/playerManager/queryBlockChats',
  /** 
   * 通过服务器id查询全部禁言列表
  */
 QueryBlockChatsBySrv: '/playerManager/queryBlockChatsBySrv',
  /** 
   * 查询玩家封号信息
  */
 QueryBlockPlayer: '/playerManager/queryBlockPlayer',
  /** 
   * 查询封号列表
  */
 QueryBlockPlayers: '/playerManager/queryBlockPlayers',
  /** 
   * 通过服务器id查询封号列表
  */
  QueryBlockPlayersBySrv: '/playerManager/queryBlockPlayersBySrv',
  /** 
   * 解除禁言
  */
 UnBlockChat: '/playerManager/unBlockChat',
  /** 
   * 解禁封号
  */
 UnBlockPlayer: '/playerManager/unBlockPlayer',
  /** 
   * 添加屏蔽词
  */
 AddBlockWord: '/chatManager/addBlockWord',
  /** 
   * 获取屏蔽词库
  */
 GetBlockWords: '/chatManager/getBlockWords',
  /** 
   * 删除屏蔽词
  */
 RemoveBlockWord: '/chatManager/removeBlockWord',
 /** 
   * 滚动显示聊天记录
  */
 ScrollChatMsg: '/chatManager/scroll',
 /** 
   * 聊天记录搜索
  */
 SearchRecord: '/chatManager/searchRecord',
 /** 
   * 获取排行榜信息
  */
 GetRankInfo: 'playerManager/getRankInfo',
 /** 
   * 查询玩家道具
  */
 QueryPlayerItems: '/playerManager/queryPlayerItems',
 /** 
   * 查询玩家装备
  */
 QueryPlayerEquips: '/playerManager/queryPlayerEquips',
  /** 
   * 充值金额排名统计
  */
 ChargeRank: '/cs/chargeRank',
  /** 
   * 发送全服邮件
  */
 SendGmNoticeMail: '/cs/sendGmNoticeMail',
  /** 
   * 服务器列表
  */
 ListServers: '/gameServer/listServers',
   /** 
   * 增加服务器
  */
 AddServer: '/gameServer/addServer',
   /** 
   * 修改服务器
  */
 UpdateServer: '/gameServer/updateServer',

 /** 
   * 刷新服务器数值表
  */
 ReloadBaseData: '/gameServer/reloadBaseData',
 /**
  * 增加角色服务器
  */
 AddRoleServer: '/roleGameServer/addServer',
 /**
  * 获取我的服务器列表
  */
 GetMyServers: '/roleGameServer/getMyServers',
 /**
  * 获取用户拥有的服务器列表
  */
 ListRoleServers: '/roleGameServer/listServers',
 /**
  * 删除角色服务器
  */
 RemoveRoleServer: '/roleGameServer/removeServer',
  /** 
   * 查询登录界面公告
  */
 GetLoginNotice: '/noticeManager/getLoginNotice',
  /** 
   * 设置登录界面公告
  */
 SetLoginNoticer: '/noticeManager/setLoginNoticer',
  /** 
   * 查询游戏内公告
  */
 GetGameNotice: '/noticeManager/getGameNotice',
 /** 
   * 设置游戏内公告
  */
 SetGameNotice: '/noticeManager/setGameNotice',

  /** 
   * 添加跑马灯公告
  */
 AddChatNotice: '/noticeManager/addChatNotice',
 /** 
   * 删除跑马灯公告
  */
 DeleteChatNotice: '/noticeManager/deleteChatNotice',
/** 
   * 获取跑马灯公告
  */
 GetChatNotices: '/noticeManager/getChatNotices',
 /** 
   * 发送一次性跑马灯公告
  */
 SendChatNotice: '/noticeManager/sendChatNotice',
  /** 
   * 修改跑马灯公告
  */
 UpdateChatNotice: '/noticeManager/updateChatNotice/',
  /** 
   * 本服邮件
  */
 SendOpMail: '/mailManager/sendOpMail',
  /** 
   * 发送奖励邮件
  */
 SendRewardMail: '/mailManager/sendRewardMail',
  /** 
   * 创建奖励邮件
  */
 CreateRewardMail: '/mailManager/createRewardMail',
  /** 
   * 删除奖励邮件
  */
 DeleteRewardMail: '/mailManager/deleteRewardMail',
  /** 
   * 查看我的奖励邮件列表
  */
 ListRewardMail: '/mailManager/listRewardMail',
  /** 
   * 修改奖励邮件
  */
 UpdateRewardMail: '/mailManager/updateRewardMail',
  /** 
   * 查看所有奖励邮件
  */
 ListRewardMailReview: '/mailManager/listRewardMailReview',
   /** 
   * 审核奖励邮件
  */
 ReviewRewardMail: '/mailManager/reviewRewardMail',
  /** 
   * 玩家内比日志
  */
 QueryGmGoldLog: '/gamelog/queryPlayerGmGoldLog',
  /** 
   * 混元珠日志
  */
 QueryPlayerHyzLog: '/gamelog/queryPlayerHyzLog',
 /** 
   * 战斗力日志
  */
 QueryPowerLog: '/gamelog/queryPowerLog',

 /** 
   * 获取我的服务器列表
  */
 GslistServers: '/gsOperator/listServers',
/** 
   * 设置登录服关闭提示
  */
 SetLoginServerStopDesc: '/gsOperator/setLoginServerStopDesc',
/** 
 * 增加白名单用户
*/
 AddWhiteAccount: '/whitelist/addWhiteAccount',
 /** 
 * 查看登录服入口开关
*/
GetLoginGateStatus: '/whitelist/getLoginGateStatus',
/** 
 * 获取所有白名单开关
*/
GetWhiteAccounts: '/whitelist/getWhiteAccounts',
/** 
 * 获取所有白名单开关
*/
RemoveWhiteAccount: '/whitelist/removeWhiteAccount',
/** 
 * 设置登录服入口开关
*/
SetLoginGateStatus: '/whitelist/setLoginGateStatus',
/**
 * 增加黑名单ip
 */
AddBlackIp: '/whitelist/addBlackIp',
/**
 * 增加白名单ip
 */
AddWhiteIp: '/whitelist/addWhiteIp',
/**
 * 获取所有黑名单ip
 */
GetBlackIps: '/whitelist/getBlackIps',
/**
 * 获取所有白名单ip
 */
GetWhiteIps: '/whitelist/getWhiteIps',
/**
 * 删除黑名单ip
 */
RemoveBlackIp: '/whitelist/removeBlackIp',
/**
 * 删除白名单ip
 */
RemoveWhiteIp: '/whitelist/removeWhiteIp',

/**
 * 删除节点
 */
DeleteNode: '/crossSrvManager/deleteNode',
/**
 * 删除世界服
 */
DeleteWorldSrv: '/crossSrvManager/deleteWorldSrv',
/**
 * 游戏服配置列表
 */
ListGameSrvInfos: '/crossSrvManager/listGameSrvInfos',
/**
 * 节点信息
 */
NodeInfo: '/crossSrvManager/nodeInfo',
/**
 * 修改节点配置
 */
UpdateNode: '/crossSrvManager/updateNode',
/**
 * 修改世界服配置
 */
UpdateWorldServer: '/crossSrvManager/updateWorldServer',
/**
 * 世界服信息
 */
WorldSrvInfo: '/crossSrvManager/worldSrvInfo',
}
export default api
