-- 用户名：xzgameop
-- 密码：Fr0D4UIzek

-- 管理后台角色
INSERT INTO `system_role`(id, role_name, remark, system_role, operator, update_time, create_time) VALUES ('2', 'game-operator', '运营管理', true, 0, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000);
-- 管理后台角色与用户关系
INSERT INTO `system_user_role`(id, user_id, role_id) VALUES ('1002', '394181970348871682', '2');

-- 管理后台角色权限
-- dashboard
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 101, '["create", "delete", "update", "query"]');
-- 数据汇总
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 301, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 302, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 303, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 304, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 305, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 306, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 307, '["create", "delete", "update", "query"]');
-- 日常数据统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 401, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 402, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 403, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 404, '["create", "delete", "update", "query"]');
-- 充值统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 501, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 502, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 503, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 504, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 505, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 506, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 507, '["create", "delete", "update", "query"]');
-- 在线统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 601, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 602, '["create", "delete", "update", "query"]');
-- 消耗统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 701, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 702, '["create", "delete", "update", "query"]');
-- 等级统计
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 801, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 802, '["create", "delete", "update", "query"]');
-- 客服
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 901, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 902, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 903, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 904, '["create", "delete", "update", "query"]');
-- 日志查询
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1001, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1002, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1003, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1004, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1005, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1006, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1007, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1008, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1009, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1010, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1011, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1012, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1013, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1014, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1015, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1016, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1017, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1018, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1019, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1020, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1021, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1022, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1023, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1024, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1025, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1026, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1027, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1028, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1029, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1030, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1031, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1032, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1033, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1034, '["create", "delete", "update", "query"]');
-- 游戏角色管理
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1101, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1102, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1103, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1104, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1105, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1106, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1107, '["create", "delete", "update", "query"]');
-- 运营管理
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1201, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1202, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1203, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1204, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1205, '["create", "delete", "update", "query"]');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (2, 1206, '["create", "delete", "update", "query"]');