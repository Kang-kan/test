INSERT INTO `system_resource`(id, menu_id, name, module_name) VALUES ('1036', '1000', '战斗力日志', 'powerLog');
INSERT INTO `system_role_right`(role_id, resource_id, auth) VALUES (1, 1036, '["create", "delete", "update", "query"]');