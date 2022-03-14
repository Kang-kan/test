DROP TABLE IF EXISTS `exp_copy_log`;
CREATE TABLE `exp_copy_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `multiple` int(11) NOT NULL COMMENT '倍数',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `time_srv_idx` (`time`,`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;