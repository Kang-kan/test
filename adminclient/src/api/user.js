import { axios } from '@/utils/request';
import api from './index'
function Stringfy(param) {
  let str = ''
  for (let key in param) {
    if (str) {
      str += ('&' + key + '=' + param[key])
    } else {
      str += (key + '=' + param[key])
    }
  }
  return str;
}
export function Test() {
  return axios({
    url: api.Test,
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 获取每日汇总
 */
export function getDailySummary(parm) {
  return axios({
    url: api.DailySummary + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 经验副本统计
 */
export function getExpCopyStatis(parm) {
  return axios({
    url: api.ExpCopyStatis,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取小时汇总
 */
export function getHourSummary(parm) {
  return axios({
    url: api.HourSummary + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 获取月活跃汇总
 */
export function getMonthActiveSummary(parm) {
  return axios({
    url: api.MonthylyActive + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 区服充值汇总
 */
export function getSvrChargSummary(parm) {
  return axios({
    url: api.ServerCharge,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取各服时间段充值汇总
 */
export function getSvrTimeSummary(parm) {
  return axios({
    url: api.ServerListCharge + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 到达创角界面人数
 */
export function createRoleUiCounter(parm) {
  return axios({
    url: api.CreateRoleUiCounter,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取单服数据
 */
export function getSingleSvrSummary(parm) {
  return axios({
    url: api.SingleSrv + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 获取当日汇总
 */
export function getTodaySummary(parm) {
  return axios({
    url: api.TodaySummary + '?' + Stringfy(parm),
    method: 'get'
    // params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取充值统计数据
 */
export function getChargeStatis(parm) {
  return axios({
    url: api.ChargeStatics,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取注册统计数据
 */
export function getRegistStatis(parm) {
  return axios({
    url: api.RegisterStatis + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 获取日常在线统计
 */
export function getDailyOnlineStatis(parm) {
  return axios({
    url: api.DailyOnlineStatis + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 获取登录留存数据
 */
export function getLoginRetain(parm) {
  return axios({
    url: api.LoginRetain + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 获取实时在线数据
 */
export function getRealTimeOnline(parm) {
  return axios({
    url: api.RealTimeOnline + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 获取在线时长分布
 */
export function getOnlineTimeDist(parm) {
  return axios({
    url: api.OnlineTimeDist,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取商城消耗统计
 */
export function getMarkConsumeStatis(parm) {
  return axios({
    url: api.MarkConsumeStatis,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取仙晶消耗统计
 */
export function getGoldFunctionStatis(parm) {
  return axios({
    url: api.GoldFunctionStatis,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 获取等级流失数据
 */
export function getLevelLossStatis(parm) {
  return axios({
    url: api.LevelLoss,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 等级分布
 */
export function getLevelDistStatis(parm) {
  return axios({
    url: api.LevelDist,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 单笔充值分布
 */
export function getChargeSingleDist(parm) {
  return axios({
    url: api.ChargeSingleDist,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 单笔充值分布V1
 */
export function getChargeSingleDistV1(parm) {
  return axios({
    url: api.chargeSingleDistV1,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 单笔充值分布V1全渠道
 */
export function getChargeSingleDistsV1(parm) {
  return axios({
    url: api.chargeSingleDistsV1,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 首充等级分布
 */
export function getChargeFirstLevelDist(parm) {
  return axios({
    url: api.ChargeFirstLevelDist,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 付费等级分布
 */
export function getChargeLevelDist(parm) {
  return axios({
    url: api.ChargeLevelDist,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 付费登录流失
 */
export function getChargeLoginLoss(parm) {
  return axios({
    url: api.ChargeLoginLoss + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 累计充值分布
 */
export function getChargeTotalDist(parm) {
  return axios({
    url: api.ChargeTotalDist,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 仙晶消耗排行
 */
export function getCostGoldRank(parm) {
  return axios({
    url: api.CostGoldRank,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 月充值汇总
 */
export function getChargeMonthSummary(parm) {
  return axios({
    url: api.ChargeMonthSummary + '?' + Stringfy(parm),
    method: 'get'
  })
}
/**
 * @param {Object} parm
 * @description 通过定单id获取充值定单
 */
export function getFindOrderById(parm) {
  return axios({
    url: api.FindById,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @description 通过玩家id获取充值定单
 */
export function getFindOrderByPlayerId(parm) {
  return axios({
    url: api.FindByPlayerId,
    method: 'get',
    params: parm
  })
}
/**
 * @param {Object} parm
 * @获取单条客服平台反馈信息
 */
export function getSingleCustomerService(parm) {
  return axios({
    url: api.SingleCustomerService,
    method: 'get',
    params: parm
  })
}

/**
 * @param {Object} parm
 * @获取客服平台反馈列表信息
 */
export function getCustomerService(parm) {
  return axios({
    url: api.CustomerService,
    method: 'get',
    params: parm
  })
}

/**
 * @param {Object} parm
 * @回复客服平台留言
 */
export function postCustomerService(parm) {
  return axios({
    url: api.PostCustomerService,
    method: 'post',
    params: parm
  })
}

export function getServerInfo(parm) {
  return axios({
    url: api.GetSrvInfo,
    method: 'get',
    params: parm
  })
}
/**
 * 查询玩家登录日志
 * @param {} parm 
 */
export function getQueryLoginLog(parm) {
  return axios({
    url: api.QueryPlayerLoginLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家登出日志
* @param {} parm 
*/
export function getQueryLogoutLog(parm) {
  return axios({
    url: api.QueryPlayerLogoutLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家仙晶日志
* @param {} parm 
*/
export function getQueryGoldLog(parm) {
  return axios({
    url: api.QueryPlayerGoldLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家金币日志
* @param {} parm 
*/
export function getQueryMoneyLog(parm) {
  return axios({
    url: api.QueryPlayerMoneyLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家道具日志
* @param {} parm 
*/
export function getQueryItemLog(parm) {
  return axios({
    url: api.QueryPlayerItemLog,
    method: 'get',
    params: parm
  })
}


/**
* 查询玩家商城日志
* @param {} parm 
*/
export function getQueryMarketLog(parm) {
  return axios({
    url: api.QueryPlayerMarketLogLog,
    method: 'get',
    params: parm
  })
}

/**
* 根据道具id查询商城日志
* @param {} parm 
*/
export function getQueryMarketLogById(parm) {
  return axios({
    url: api.QueryMarketLogLogById,
    method: 'get',
    params: parm
  })
}

/**
 * 查询玩家充值日志
 * @param {} parm 
 */
export function getQueryChargeLog(parm) {
  return axios({
    url: api.QueryPlayerChargeLog,
    method: 'get',
    params: parm
  })
}

/**
 * 查询玩家充值日志
 * @param {} parm 
 */
export function exportChargeBill(parm) {
  return axios({
    url: api.ExportChargeBill,
    method: 'get',
    params: parm
  })
}

/**
 * 查询充值日志
 * @param {} parm 
 */
export function getQueryAllChargeLogs(parm) {
  return axios({
    url: api.QueryAllChargeLog,
    method: 'get',
    params: parm
  })
}
/**
* 根据定单id查找充值日志
* @param {} parm 
*/
export function getQueryChargeLogById(parm) {
  return axios({
    url: api.QueryPlayerChargeLogById,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家经验日志
* @param {} parm 
*/
export function getQueryExpLog(parm) {
  return axios({
    url: api.QueryPlayerExpLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家修为日志
* @param {} parm 
*/
export function getQueryRExpLog(parm) {
  return axios({
    url: api.QueryPlayerRExpLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家时装日志
* @param {} parm 
*/
export function getQueryFashionLog(parm) {
  return axios({
    url: api.QueryPlayerFashionLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家邮件日志
* @param {} parm 
*/
export function getQueryMailLog(parm) {
  return axios({
    url: api.QueryPlayerMailLog,
    method: 'get',
    params: parm
  })
}

/**
* 根据服务器id标题查询邮件日志
* @param {} parm 
*/
export function getQueryMailLogByTitle(parm) {
  return axios({
    url: api.QueryMailLogByTitle,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家分享日志
* @param {} parm 
*/
export function getQueryShareLog(parm) {
  return axios({
    url: api.QueryPlayerShareLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家灵压日志
* @param {} parm 
*/
export function getQueryExpliotLog(parm) {
  return axios({
    url: api.QueryPlayerExpliotLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家符文日志
* @param {} parm 
*/
export function getQuerySealStoneLog(parm) {
  return axios({
    url: api.QueryPlayerSealStoneLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家离线日志
* @param {} parm 
*/
export function getQueryOfflineLog(parm) {
  return axios({
    url: api.QueryPlayerOfflineRewardLog,
    method: 'get',
    params: parm
  })
}
/**
* 查询玩家礼包码日志
* @param {} parm 
*/
export function getQueryPlayerSerialGiftLog(parm) {
  return axios({
    url: api.QueryPlayerSerialGiftLog,
    method: 'get',
    params: parm
  })
}
/**
* 根据礼包码查询日志
* @param {} parm 
*/
export function getQuerySerialGiftLog(parm) {
  return axios({
    url: api.QuerySerialGiftLog,
    method: 'get',
    params: parm
  })
}
/**
* 根据副本日志
* @param {} parm 
*/
export function getQueryCopyLog(parm) {
  return axios({
    url: api.QueryCopyLog,
    method: 'get',
    params: parm
  })
}
/**
* 根据转生查询日志
* @param {} parm 
*/
export function getQueryRebirthLog(parm) {
  return axios({
    url: api.QueryRebirthLog,
    method: 'get',
    params: parm
  })
}

/**
* 添加角色
* @param {} parm 
*/
export function postAddRole(parm) {
  // Object.assign(parm,{id:0})
  return axios({
    url: api.AddRole,
    method: 'post',
    data: parm
  })
}

/**
* 删除角色
* @param {} parm 
*/
export function postDeleteRole(parm) {
  return axios({
    url: api.DeleteRole + '/' + parm.roleId,
    method: 'post',
    params: parm
  })
}

/**
* 更改角色信息
* @param {} parm 
*/
export function postUpdateRole(parm) {
  return axios({
    url: api.UpdateRole,
    method: 'post',
    params: parm
  })
}

/**
* 获取角色列表
* @param {} parm 
*/
export function getRoleList(parm) {
  return axios({
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    url: api.GetRoleList,
    method: 'get',
    params: parm
  })
}

/**
* 根据角色id获取角色
* @param {} parm 
*/
export function getRoleById(parm) {
  return axios({
    url: api.GetRoleById,
    method: 'get',
    params: parm
  })
}

/**
* 删除角色权限
* @param {} parm 
*/
export function deleteRoleRight(parm) {
  return axios({
    url: api.DeleteRoleRight + '/' + parm.roleId + '/' + parm.resourceId,
    method: 'delete',
    params: parm
  })
}

/**
* 获取角色权限
* @param {} parm 
*/
export function getRoleRightById(parm) {
  return axios({
    url: api.GetRoleRights + '/' + parm.roleId,
    method: 'get',
    params: parm
  })
}

/**
* 修改角色权限
* @param {} parm 
*/
export function putRoleRight(parm) {
  return axios({
    url: api.UpdateRight + '/' + parm.roleId + '/' + parm.resourceId,
    method: 'put',
    data: parm.rights.split(',')

  })
}

/**
* 添加角色用户关系
* @param {} parm 
*/
export function postAddUserRole(parm) {
  return axios({
    url: api.AddUserRole,
    method: 'post',
    params: parm
  })
}

/**
* 删除角色用户关系
* @param {} parm 
*/
export function deleteUserRole(parm) {
  return axios({
    url: api.DeleteUserRole,
    method: 'delete',
    params: parm
  })
}

/**
* 获取本人角色用户关系
* @param {} parm 
*/
export function getMyRoles(parm) {
  return axios({
    url: api.GetMyroles,
    method: 'get',
    params: parm
  })
}

/**
* 根据用户id获取角色用户关系
* @param {} parm 
*/
export function getRolesByUser(parm) {
  return axios({
    url: api.GetRolesByUser + '/' + parm.userId,
    method: 'get',
    params: { userId: parm.userId }
  })
}

/**
* 根据角色id获取角色用户关系
* @param {} parm 
*/
export function getUserByRole(parm) {
  return axios({
    url: api.GetUsersByRole + '/' + parm.roleId,
    method: 'get',
    params: { roleId: parm.roleId }
  })
}
//=============
/**
* 查询仙盟日志
* @param {} parm 
*/
export function getFamilyLog(parm) {
  return axios({
    url: api.QueryFamilyLog,
    method: 'get',
    params: parm
  })
}

/**
* 根据id或name查询仙盟日志
* @param {} parm 
*/
export function getFamilyLogById(parm) {
  return axios({
    url: api.QueryFamilyLogById,
    method: 'get',
    params: parm
  })
}

/**
* 查询其他货币日志
* @param {} parm 
*/
export function getOtherResourceLog(parm) {
  return axios({
    url: api.QueryOtherResourceLog + '?' + Stringfy(parm),
    method: 'get'
  })
}

/**
* 查询成就日志
* @param {} parm 
*/
export function getAchieveLog(parm) {
  return axios({
    url: api.QueryPlayerAchieveLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询称号日志
* @param {} parm 
*/
export function getPlayerTitleLog(parm) {
  return axios({
    url: api.QueryPlayerTitleLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询神器日志
* @param {} parm 
*/
export function getChopLog(parm) {
  return axios({
    url: api.QueryPlayerChopLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询异闻录日志
* @param {} parm 
*/
export function getPokedexLog(parm) {
  return axios({
    url: api.QueryPlayerPokedexLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询圣魂守护兽日志
* @param {} parm 
*/
export function getPracticeLog(parm) {
  return axios({
    url: api.QueryPlayerPracticeLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询技能日志
* @param {} parm 
*/
export function getPlayerSkillLog(parm) {
  return axios({
    url: api.QueryPlayerSkillLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询心法技能日志日志
* @param {} parm 
*/
export function getPlayerSkillBookLog(parm) {
  return axios({
    url: api.QueryPlayerSkillBookLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询星宿日志
* @param {} parm 
*/
export function getPlayerMeridianLog(parm) {
  return axios({
    url: api.QueryPlayerMeridianLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询强化日志
* @param {} parm 
*/
export function getPlayerEnhanceLog(parm) {
  return axios({
    url: api.QueryPlayerEnhanceLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询器灵日志
* @param {} parm 
*/
export function getPlayerWeaponSoulLog(parm) {
  return axios({
    url: api.QueryPlayerWeaponSoulLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询仙兽蕴养日志
* @param {} parm 
*/
export function getPlayerTotemLog(parm) {
  return axios({
    url: api.QueryPlayerTotemLog,
    method: 'get',
    params: parm
  })
}

/**
* 查询仙兽灵感日志
* @param {} parm 
*/
export function getPlayerTotemExpandLog(parm) {
  return axios({
    url: api.QueryPlayerTotemExpandLog,
    method: 'get',
    params: parm
  })
}
// ========================
/**
* 根据战斗id查找boss击杀日志
* @param {} parm 
*/
export function getQueryBossDeadLogByBattleId(parm) {
  return axios({
    url: api.QueryBossDeadLogByBattleId,
    method: 'get',
    params: parm
  })
}

/**
* 根据服务器id、玩法类型查找boss击杀日志
* @param {} parm 
*/
export function getQueryBossDeadLogBySrvAndType(parm) {
  return axios({
    url: api.QueryBossDeadLogBySrvAndType,
    method: 'get',
    params: parm
  })
}

/**
* 查询玩家boss击杀日志
* @param {} parm 
*/
export function getQueryPlayerBossDeadLog(parm) {
  return axios({
    url: api.QueryPlayerBossDeadLog,
    method: 'get',
    params: parm
  })
}

//=============================


/**
* 生成礼包码
* @param {} parm 
*/
export function getCreateGift(parm) {
  return axios({
    url: api.CreateGift,
    method: 'put',
    data: parm
  })
}

/**
* 查询可用礼包码
* @param {} parm 
*/
export function getQueryGift(parm) {
  return axios({
    url: api.QueryGift,
    method: 'get',
    params: parm
  })
}

/**
* 查询指定礼包码
* @param {} parm 
*/
export function getQueryGiftCode(parm) {
  return axios({
    url: api.QueryGiftCode,
    method: 'get',
    params: parm
  })
}

/**
* 生成礼包
* @param {} parm 
*/
export function getCreateGiftRewards(parm) {
  return axios({
    url: api.CreateGiftRewards,
    method: 'put',
    data: parm
  })
}

/**
* 查询所有礼包
* @param {} parm 
*/
export function getQueryGiftRewards(parm) {
  return axios({
    url: api.QueryGiftRewards,
    method: 'get',
    params: parm
  })
}

/**
* 修改礼包
* @param {} parm 
*/
export function updataGiftRewards(parm) {
  return axios({
    url: api.UpdataGiftRewards,
    method: 'post',
    data: parm
  })
}

/**
* 仙盟列表
* @param {} parm 
*/
export function getListFamily(parm) {
  return axios({
    url: api.ListFamily,
    method: 'get',
    params: parm
  })
}

/**
* 修改仙盟公告
* @param {} parm 
*/
export function getUpdateFamily(parm) {
  return axios({
    url: api.UpdateFamily,
    method: 'get',
    params: parm
  })
}

/**
* 角色列表
* @param {} parm 
*/
export function getPlayerList(parm) {
  return axios({
    url: api.PlayerList,
    method: 'get',
    params: parm
  })
}

/**
* 查询玩家基本信息
* @param {} parm 
*/
export function getQueryPlayerInfos(parm) {
  return axios({
    url: api.QueryPlayerInfos,
    method: 'get',
    params: parm
  })
}

/**
* 封号
* @param {} parm 
*/
export function getBlockPlayer(parm) {
  return axios({
    url: api.BlockPlayer,
    method: 'put',
    params: parm
  })
}

/**
* 禁言
* @param {} parm 
*/
export function getBlockChat(parm) {
  return axios({
    url: api.BlockChat,
    method: 'put',
    params: parm
  })
}

//========================================
/**
* 查询玩家禁言列表
* @param {} parm 
*/
export function queryPlayerBlockChat(parm) {
  return axios({
    url: api.QueryPlayerBlockChat,
    method: 'get',
    params: parm
  })
}

/**
* 查询全部禁言列表
* @param {} parm 
*/
export function queryBlockChats(parm) {
  return axios({
    url: api.QueryBlockChats,
    method: 'get',
    params: parm
  })
}

/**
* 通过服务器id查询全部禁言列表
* @param {} parm 
*/
export function queryBlockChatsBySrv(parm) {
  return axios({
    url: api.QueryBlockChatsBySrv,
    method: 'get',
    params: parm
  })
}

/**
* 查询玩家封号信息
* @param {} parm 
*/
export function queryBlockPlayer(parm) {
  return axios({
    url: api.QueryBlockPlayer,
    method: 'get',
    params: parm
  })
}

/**
* 查询封号列表
* @param {} parm 
*/
export function queryBlockPlayers(parm) {
  return axios({
    url: api.QueryBlockPlayers,
    method: 'get',
    params: parm
  })
}

/**
* 通过服务器id查询封号列表
* @param {} parm 
*/
export function queryBlockPlayersBySrv(parm) {
  return axios({
    url: api.QueryBlockPlayersBySrv,
    method: 'get',
    params: parm
  })
}

/**
* 解除禁言
* @param {} parm 
*/
export function unBlockChat(parm) {
  return axios({
    url: api.UnBlockChat,
    method: 'delete',
    params: parm
  })
}

/**
* 解禁封号
* @param {} parm 
*/
export function unBlockPlayer(parm) {
  return axios({
    url: api.UnBlockPlayer,
    method: 'delete',
    params: parm
  })
}

/**
* 添加屏蔽词
* @param {} parm 
*/
export function addBlockWord(parm) {
  return axios({
    url: api.AddBlockWord,
    method: 'put',
    params: parm
  })
}

/**
* 获取屏蔽词库
* @param {} parm 
*/
export function getBlockWords(parm) {
  return axios({
    url: api.GetBlockWords,
    method: 'get',
    params: parm
  })
}

/**
* 删除屏蔽词
* @param {} parm 
*/
export function removeBlockWord(parm) {
  return axios({
    url: api.RemoveBlockWord,
    method: 'delete',
    params: parm
  })
}

/**
* 滚动显示聊天记录
* @param {} parm 
*/
export function scrollChatMsg(parm) {
  return axios({
    url: api.ScrollChatMsg,
    method: 'post',
    data: parm
  })
}

/**
* 聊天记录搜索
* @param {} parm 
*/
export function searchRecord(parm) {
  return axios({
    url: api.SearchRecord,
    method: 'post',
    data: parm
  })
}

/**
* 获取排行榜信息
* @param {} parm 
*/
export function getGetRankInfo(parm) {
  return axios({
    url: api.GetRankInfo,
    method: 'get',
    params: parm
  })
}

/**
* 查询玩家道具
* @param {} parm 
*/
export function getQueryPlayerItems(parm) {
  return axios({
    url: api.QueryPlayerItems,
    method: 'get',
    params: parm
  })
}

/**
* 查询玩家装备
* @param {} parm 
*/
export function getQueryPlayerEquips(parm) {
  return axios({
    url: api.QueryPlayerEquips,
    method: 'get',
    params: parm
  })
}

/**
* 充值排名统计
* @param {} parm 
*/
export function getChargeRank(parm) {
  return axios({
    url: api.ChargeRank,
    method: 'get',
    params: parm
  })
}

/**
* 发送全服邮件
* @param {} parm 
*/
export function getSendGmNoticeMail(parm) {
  return axios({
    url: api.SendGmNoticeMail,
    method: 'post',
    params: parm
  })
}

/**
* 增加服务器
* @param {} parm 
*/
export function getAddServer(parm) {
  return axios({
    url: api.AddServer,
    method: 'put',
    data: parm
  })
}

/**
* 服务器列表
* @param {} parm 
*/
export function getListServers(parm) {
  return axios({
    url: api.ListServers,
    method: 'get',
    params: parm
  })
}

/**
* 修改服务器
* @param {} parm 
*/
export function updateServer(parm) {
  return axios({
    url: api.UpdateServer,
    method: 'post',
    data: parm
  })
}

/**
* 刷新服务器数值表
* @param {} parm 
*/
export function reloadBaseData(parm) {
  return axios({
    url: api.ReloadBaseData,
    method: 'post',
    data: parm
  })
}

//===============
/**
* 增加用户服务器
* @param {} parm 
*/
export function addRoleServer(parm) {
  return axios({
    url: api.AddRoleServer + '/' + parm.roleId,
    method: 'put',
    params: parm
  })
}
/**
* 获取我的服务器列表
* @param {} parm 
*/
export function getMyServers(parm) {
  return axios({
    url: api.GetMyServers,
    method: 'get',
    params: parm
  })
}
/**
* 获取角色拥有的服务器列表
* @param {} parm 
*/
export function listRoleServers(parm) {
  return axios({
    url: api.ListRoleServers + '/' + parm.roleId,
    method: 'get',
    params: parm
  })
}
/**
* 删除角色服务器
* @param {} parm 
*/
export function removeRoleServer(parm) {
  return axios({
    url: api.RemoveRoleServer + '/' + parm.roleId,
    method: 'delete',
    params: parm
  })
}

//=============

/**
* 查询登录界面公告
* @param {} parm 
*/
export function getLoginNotice(parm) {
  return axios({
    url: api.GetLoginNotice,
    method: 'get',
    data: parm
  })
}

/**
* 设置登录界面公告
* @param {} parm 
*/
export function setLoginNoticer(parm) {
  return axios({
    url: api.SetLoginNoticer,
    method: 'post',
    data: parm
  })
}

/**
* 查询游戏内公告
* @param {} parm 
*/
export function getGameNotice(parm) {
  return axios({
    url: api.GetGameNotice,
    method: 'get',
    data: parm
  })
}

/**
* 设置游戏内公告
* @param {} parm 
*/
export function setGameNotice(parm) {
  return axios({
    url: api.SetGameNotice,
    method: 'post',
    data: parm
  })
}

/**
* 添加跑马灯公告
* @param {} parm 
*/
export function addChatNotice(parm) {
  return axios({
    url: api.AddChatNotice,
    method: 'put',
    data: parm
  })
}

/**
* 删除跑马灯公告
* @param {} parm 
*/
export function deleteChatNotice(parm) {
  return axios({
    url: api.DeleteChatNotice + "/" + parm.chatNoticeId,
    method: 'delete',
    params: parm
  })
}

/**
* 获取跑马灯公告
* @param {} parm 
*/
export function getChatNotices(parm) {
  return axios({
    url: api.GetChatNotices,
    method: 'get',
    params: parm
  })
}

/**
* 发送一次性跑马灯公告
* @param {} parm 
*/
export function sendChatNotice(parm) {
  return axios({
    url: api.SendChatNotice,
    method: 'post',
    data: parm
  })
}

/**
* 修改跑马灯公告
* @param {} parm 
*/
export function updateChatNotice(parm) {
  return axios({
    url: api.UpdateChatNotice + '/' + parm.chatNoticeId,
    method: 'post',
    data: parm
  })
}

// =======================================

/**
* 发送本服邮件
* @param {} parm 
*/
export function sendOpMail(parm) {
  return axios({
    url: api.SendOpMail + '/' + parm.serverId,
    method: 'post',
    params: parm
  })
}

/**
* 发送奖励邮件
* @param {} parm 
*/
export function sendRewardMail(parm) {
  return axios({
    url: api.SendRewardMail,
    method: 'post',
    params: parm
  })
}

/**
* 创建奖励邮件
* @param {} parm 
*/
export function createRewardMail(parm) {
  return axios({
    url: api.CreateRewardMail,
    method: 'post',
    data: parm
  })
}

/**
* 删除奖励邮件
* @param {} parm 
*/
export function deleteRewardMail(parm) {
  return axios({
    url: api.DeleteRewardMail + '/' + parm.rewardMailId,
    method: 'delete',
    params: parm
  })
}

/**
* 查看我的奖励邮件列表
* @param {} parm 
*/
export function getListRewardMail(parm) {
  return axios({
    url: api.ListRewardMail,
    method: 'get',
    params: parm
  })
}

/**
* 修改奖励邮件
* @param {} parm 
*/
export function updateRewardMail(parm) {
  return axios({
    url: api.UpdateRewardMail + '/' + parm.rewardMailId,
    method: 'post',
    data: parm
  })
}

/**
* 查看所有奖励邮件
* @param {} parm 
*/
export function getListRewardMailReview(parm) {
  return axios({
    url: api.ListRewardMailReview,
    method: 'get',
    params: parm
  })
}

/**
* 审核奖励邮件
* @param {} parm 
*/
export function reviewRewardMail(parm) {
  return axios({
    url: api.ReviewRewardMail,
    method: 'post',
    params: parm
  })
}


/**
* 查看内比日志
* @param {} parm 
*/
export function getQueryGmGoldLog(parm) {
  return axios({
    url: api.QueryGmGoldLog,
    method: 'get',
    params: parm
  })
}

/**
* 混元珠日志
* @param {} parm 
*/
export function getQueryPlayerHyzLog(parm) {
  return axios({
    url: api.QueryPlayerHyzLog,
    method: 'get',
    params: parm
  })
}

/**
* 战斗力日志
* @param {} parm 
*/
export function getQueryPowerLog(parm) {
  return axios({
    url: api.QueryPowerLog,
    method: 'post',
    data: parm
  })
}

/**
* 获取我的服务器列表
* @param {} parm 
*/
export function getGslistServers(parm) {
  return axios({
    url: api.GslistServers,
    method: 'get',
    params: parm
  })
}

/**
* 设置登录服关闭提示
* @param {} parm 
*/
export function setLoginServerStopDesc(parm) {
  return axios({
    url: api.SetLoginServerStopDesc + '?' + Stringfy(parm),
    method: 'post'
  })
}

// ====================

/**
* 增加白名单用户
* @param {} parm 
*/
export function addWhiteAccount(parm) {
  return axios({
    url: api.AddWhiteAccount,
    method: 'put',
    params: parm
  })
}

/**
* 查看登录服入口开关
* @param {} parm 
*/
export function getLoginGateStatus(parm) {
  return axios({
    url: api.GetLoginGateStatus,
    method: 'get',
    params: parm
  })
}

/**
* 获取所有白名单用户
* @param {} parm 
*/
export function getWhiteAccounts(parm) {
  return axios({
    url: api.GetWhiteAccounts,
    method: 'get',
    params: parm
  })
}

/**
* 删除白名单用户
* @param {} parm 
*/
export function removeWhiteAccount(parm) {
  return axios({
    url: api.RemoveWhiteAccount,
    method: 'delete',
    params: parm
  })
}

/**
* 设置登录服入口开关
* @param {} parm 
*/
export function setLoginGateStatus(parm) {
  return axios({
    url: api.SetLoginGateStatus,
    method: 'post',
    params: parm
  })
}

//==============================
/**
* 增加黑名单ip
* @param {} parm 
*/
export function addBlackIp(parm) {
  return axios({
    url: api.AddBlackIp,
    method: 'put',
    params: parm
  })
}

/**
* 增加白名单ip
* @param {} parm 
*/
export function addWhiteIp(parm) {
  return axios({
    url: api.AddWhiteIp,
    method: 'put',
    params: parm
  })
}

/**
* 获取所有黑名单ip
* @param {} parm 
*/
export function getBlackIps(parm) {
  return axios({
    url: api.GetBlackIps,
    method: 'get',
    params: parm
  })
}

/**
* 获取所有白名单ip
* @param {} parm 
*/
export function getWhiteIps(parm) {
  return axios({
    url: api.GetWhiteIps,
    method: 'get',
    params: parm
  })
}

/**
* 删除黑名单ip
* @param {} parm 
*/
export function removeBlackIp(parm) {
  return axios({
    url: api.RemoveBlackIp,
    method: 'delete',
    params: parm
  })
}

/**
* 删除白名单ip
* @param {} parm 
*/
export function removeWhiteIp(parm) {
  return axios({
    url: api.RemoveWhiteIp,
    method: 'delete',
    params: parm
  })
}
//=================================
/**
* 删除节点
* @param {} parm 
*/
export function deleteNode(parm) {
  return axios({
    url: api.DeleteNode,
    method: 'delete',
    params: parm
  })
}

/**
* 删除世界服
* @param {} parm 
*/
export function deleteWorldSrv(parm) {
  return axios({
    url: api.DeleteWorldSrv,
    method: 'delete',
    params: parm
  })
}

/**
* 游戏服配置列表
* @param no param
*/
export function getListGameSrvInfos(parm) {
  return axios({
    url: api.ListGameSrvInfos,
    method: 'get'
  })
}

/**
* 节点信息
* @param {} parm 
*/
export function getNodeInfo(parm) {
  return axios({
    url: api.NodeInfo,
    method: 'get',
    params: parm
  })
}

/**
* 修改节点配置
* @param {} parm 
*/
export function updateNode(parm) {
  return axios({
    url: api.UpdateNode,
    method: 'post',
    params: parm
  })
}

/**
* 修改世界服配置
* @param {} parm 
*/
export function updateWorldServer(parm) {
  return axios({
    url: api.UpdateWorldServer,
    method: 'post',
    data: parm
  })
}

/**
* 世界服信息
* @param {} no param 
*/
export function getWorldSrvInfo() {
  return axios({
    url: api.WorldSrvInfo,
    method: 'get'
  })
}

/**
 * 修改密码
 * @param oldPassword
 * @param newPassword
 */
export function changePassword(oldPassword, newPassword) {
  return axios({
    url: '/systemuser/changePassword',
    method: 'post',
    data: { 'oldPassword': oldPassword, 'newPassword': newPassword }
  })
}
