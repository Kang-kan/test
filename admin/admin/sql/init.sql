-- 菜单 id只能增加删除，不能修改
INSERT INTO `system_menu`(id, name) VALUES ('100', 'dashboard');
INSERT INTO `system_menu`(id, name) VALUES ('200', '系统管理');
INSERT INTO `system_menu`(id, name) VALUES ('300', '数据汇总');
INSERT INTO `system_menu`(id, name) VALUES ('400', '日常数据统计');
INSERT INTO `system_menu`(id, name) VALUES ('500', '充值统计');
INSERT INTO `system_menu`(id, name) VALUES ('600', '在线统计');
INSERT INTO `system_menu`(id, name) VALUES ('700', '消耗统计');
INSERT INTO `system_menu`(id, name) VALUES ('800', '等级统计');
INSERT INTO `system_menu`(id, name) VALUES ('900', '客服');
INSERT INTO `system_menu`(id, name) VALUES ('1000', '日志查询');
INSERT INTO `system_menu`(id, name) VALUES ('1100', '游戏角色管理');
INSERT INTO `system_menu`(id, name) VALUES ('1200', '运营管理');

-- dashboard
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('101', '100', '工作台', 'workplace');
-- 系统管理
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('201', '200', '用户管理', 'systemUser');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('202', '200', '角色管理', 'systemRole');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('203', '200', '角色权限管理', 'systemRoleRight');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('204', '200', '角色权限管理', 'systemUserRole');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('205', '200', '服务器管理', 'gameServer');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('206', '200', '角色服务器管理', 'roleGameServer');
-- 数据汇总
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('301', '300', '当日汇总', 'todySummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('302', '300', '小时汇总', 'hourSummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('303', '300', '每日汇总', 'dailySummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('304', '300', '单服数据简表', 'singleSrvSummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('305', '300', '区服充值汇总', 'singleSrvChargeSummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('306', '300', '月活跃汇总', 'monthlyActiveSummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('307', '300', '各服时间段充值', 'srvsChargeSummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('308', '300', '到达创角界面人数', 'createRoleUiCounter');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('309', '300', '经验副本统计', 'expCopyStatis');
-- 日常数据统计
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('401', '400', '登录留存', 'loginRetain');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('402', '400', '注册统计', 'registStatis');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('403', '400', '在线统计', 'onlineStatis');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('404', '400', '充值统计', 'chargeStatis');
-- 充值统计
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('501', '500', '月充值汇总', 'chargeMonthSummary');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('502', '500', '单笔充值分布', 'chargeSingleDist');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('503', '500', '累计充值分布', 'chargeTotalDist');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('504', '500', '首充等级分布', 'chargeFirstLevelDist');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('505', '500', '付费等级分布', 'chargeLevelDist');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('506', '500', '付费登录流失', 'chargeLoginLoss');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('507', '500', '元宝消耗排行', 'costGoldRank');
-- 在线统计
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('601', '600', '实时在线', 'realTimeOnline');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('602', '600', '在线时长分布', 'onlineTimeDist');
-- 消耗统计
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('701', '700', '元宝消耗统计', 'goldFunctionStatis');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('702', '700', '商城消耗统计', 'markConsumeStatis');
-- 等级统计
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('801', '800', '等级分布', 'leveldist');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('802', '800', '等级流失', 'levelloss');
-- 客服
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('901', '900', '获取留言列表', 'csMessage');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('902', '900', '回复留言', 'replyCsMessage');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('903', '900', '仙盟列表', 'listFamily');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('904', '900', '修改仙盟公告', 'updateFamilyNotice');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('905', '900', '充值金额排名统计', 'chargeRank');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('906', '900', '发送全服邮件', 'sendGmNoticeMail');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('907', '900', '聊天管理', 'chatManager');
-- 日志查询
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1001', '1000', '玩家登录日志', 'queryPlayerLoginLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1002', '1000', '玩家登出日志', 'queryPlayerLogoutLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1003', '1000', '玩家仙晶日志', 'queryPlayerGoldLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1004', '1000', '玩家金币日志', 'queryPlayerMoneyLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1005', '1000', '玩家道具日志', 'queryPlayerItemLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1006', '1000', '玩家商城日志', 'querPlayerMarketLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1007', '1000', '玩家充值日志', 'querPlayerChargeLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1008', '1000', '玩家经验日志', 'querPlayerExpLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1009', '1000', '玩家修为日志', 'querPlayerRExpLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1010', '1000', '玩家时装日志', 'querPlayerFashionLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1011', '1000', '玩家邮件日志', 'querPlayerMailLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1012', '1000', '玩家分享日志', 'querPlayerShareLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1013', '1000', '玩家灵压日志', 'querPlayerExpliotLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1014', '1000', '玩家符文日志', 'querPlayerSealStoneLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1015', '1000', '玩家离线挂机日志', 'querPlayerOfflineRewardLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1016', '1000', '玩家礼包码日志', 'querPlayerSerialGiftLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1017', '1000', '玩家副本日志', 'querPlayerCopyLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1018', '1000', '玩家转生日志', 'querPlayerRebirthLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1019', '1000', '玩家仙盟日志', 'querFamilyLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1020', '1000', '玩家其它货币日志', 'querOtherResourceLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1021', '1000', '玩家成就日志', 'querPlayerAchieveLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1022', '1000', '玩家称号日志', 'querPlayerTitleLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1023', '1000', '玩家神器日志', 'querPlayerChopLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1024', '1000', '玩家异闻录日志', 'querPlayerPokedexLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1025', '1000', '玩家伙伴圣魂守护兽日志', 'querPlayerPracticeLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1026', '1000', '玩家技能日志', 'queryPlayerSkillLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1027', '1000', '玩家心法技能日志', 'queryPlayerSkillBookLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1028', '1000', '玩家星宿日志', 'queryPlayerMeridianLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1029', '1000', '玩家强化日志', 'queryPlayerEnhanceLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1030', '1000', '玩家器灵日志', 'queryPlayerWeaponSoulLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1031', '1000', '玩家仙兽蕴养日志', 'queryPlayerTotemLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1032', '1000', '玩家仙兽灵感日志', 'queryPlayerTotemExpandLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1033', '1000', 'boss击杀日志', 'queryBossDeadLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1034', '1000', '玩家内币日志', 'queryPlayerGmGoldLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1035', '1000', '玩家混元珠日志', 'queryPlayerHyzLog');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1036', '1000', '战斗力日志', 'powerLog');
-- 游戏角色管理
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1101', '1100', '角色列表', 'playerManager');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1102', '1100', '查询玩家基本信息', 'queryPlayerInfos');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1103', '1100', '封号', 'blockPlayer');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1104', '1100', '禁言', 'blockChat');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1105', '1100', '获取排行榜信息', 'getRankInfo');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1106', '1100', '查询玩家道具', 'queryPlayerItems');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1107', '1100', '查询玩家装备', 'queryPlayerEquips');
-- 运营管理
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1201', '1200', '生成礼包', 'createGift');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1202', '1200', '查询可用礼包码', 'queryGift');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1203', '1200', '登录界面公告', 'loginNoticeManager');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1204', '1200', '游戏内公告', 'gameNoticeManager');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1205', '1200', '跑马灯公告', 'chatNotice');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1206', '1200', '发送本服邮件', 'opMailManager');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1207', '1200', '全服奖励邮件', 'opRewardMail');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1208', '1200', '审核奖励邮件', 'opRewardMailReview');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1209', '1200', '服务器列表', 'gsOperator');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1210', '1200', '白名单', 'whitelist');
INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1211', '1200', '跨服管理', 'crossSrvManager');

-- 系统内置用户
SET @salt = '81BcAB18A2kBL9QBAGY9+ctwUTEXKRU0hFAuJVCsyqRCB68kxirsFJ0qmK7UMiqrGfCHIFWuUtFo1vEvPWWbwum+pRt2RH3pgvIp2etkFwNEGfdvwAi6n37iqrf+tIeQ3FQicBsQaPXAre9+zFHXmrt53veAE0xWaimFn6Rj0Zgd74JsUU+b+U7f1YZ890PwtHckSlNSodyTXC3GAAxj/pzPyq6X41jAaY/B8CWkEBKfNcaUI837xXvrBGhazgZvWUhxSZnJ/WIu32FF7B2K/6v9QyT0bumOlOo+JVITQ66498v+gupDmJV2IFPZg7vVxI/merUkke5OEv4kucMRoIbIhPh6mysk5yK25vACWsqpBQ5L23QnyfRjAMHQMrAevhzng0Yub11rXjQUaToZpQWB5rBDQxUTce1ZIfSg4lqTcHICbxRJ6gjTGX2b9tegrWowji7X5cxvWBk8uXTRIl2A03Zr81zrA3sQjxoJc/2RZOxgGVQyGkxBQ+nSJYS5ePlqsfXyq3SAt6dhbJ4eXYr9XMlyJVvG1VhZSBFgC5ViWMUpICxS8YTmeUCPhSifWXnyKOICldcEZE/MvTCyhyZrfPCanVFP+U8+uiIgAO3s8nveNE5qUqXrMtmvEhx46uFdtayk07NsydzDOomPxA1PIxGimprRX1tuQrY8bI4=';
SET @pwd = 'ZFunXxX0x6936HnyhiJSFWpMEA9ChrJtJQQ6GbprbFPeCdBYwWMqx+w4sRkVSFhuTMTZ4xtViX4S9TtUDVcWYfEksvoF8ApWZspfokGV/0w=';
INSERT INTO `system_user`(id, user_name, password, salt, name, phone, status, system_user, operator, create_time)
VALUES ('1001', 'admin', FROM_BASE64(@pwd), FROM_BASE64(@salt), '系统管理员', '', 1, 1, 0, UNIX_TIMESTAMP() * 1000);

-- 管理后台角色
INSERT INTO `system_role`(id, role_name, remark, system_role, operator, update_time, create_time) VALUES ('1', 'admin', '系统管理员', true, 0, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000);

-- 管理后台角色与用户关系
INSERT INTO `system_user_role`(id, user_id, role_id) VALUES ('1001', '1001', '1');

-- 管理后台角色权限
-- dashboard
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 101, '["create", "delete", "update", "query"]');
-- 系统管理
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 201, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 202, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 203, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 204, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 205, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 206, '["create", "delete", "update", "query"]');
-- 数据汇总
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 301, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 302, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 303, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 304, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 305, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 306, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 307, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 308, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 309, '["create", "delete", "update", "query"]');
-- 日常数据统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 401, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 402, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 403, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 404, '["create", "delete", "update", "query"]');
-- 充值统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 501, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 502, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 503, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 504, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 505, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 506, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 507, '["create", "delete", "update", "query"]');
-- 在线统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 601, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 602, '["create", "delete", "update", "query"]');
-- 消耗统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 701, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 702, '["create", "delete", "update", "query"]');
-- 等级统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 801, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 802, '["create", "delete", "update", "query"]');
-- 客服
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 901, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 902, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 903, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 904, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 905, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 906, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 907, '["create", "delete", "update", "query"]');
-- 日志查询
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1001, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1002, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1003, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1004, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1005, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1006, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1007, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1008, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1009, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1010, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1011, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1012, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1013, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1014, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1015, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1016, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1017, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1018, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1019, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1020, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1021, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1022, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1023, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1024, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1025, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1026, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1027, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1028, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1029, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1030, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1031, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1032, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1033, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1034, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1035, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1036, '["create", "delete", "update", "query"]');
-- 游戏角色管理
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1101, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1102, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1103, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1104, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1105, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1106, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1107, '["create", "delete", "update", "query"]');
-- 运营管理
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1201, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1202, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1203, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1204, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1205, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1206, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1207, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1208, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1209, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1210, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1211, '["create", "delete", "update", "query"]');