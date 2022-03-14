-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: logsrv1
-- ------------------------------------------------------
-- Server version	5.7.28-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `achieve_log`
--

DROP TABLE IF EXISTS `achieve_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achieve_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `achieve_id` int(11) NOT NULL COMMENT '成就ID',
  `bonus` varchar(1024) DEFAULT NULL COMMENT '奖励',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charge_order_log`
--

DROP TABLE IF EXISTS `charge_order_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charge_order_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `advice_time` bigint(20) DEFAULT NULL COMMENT '通知发货时间',
  `arrival_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '到账时间',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `create_day` tinyint(1) NOT NULL COMMENT '是否新用户',
  `first_rmb` int(11) NOT NULL COMMENT '第一次充值金额',
  `game_result_code` varchar(16) DEFAULT NULL COMMENT '通知游戏服发货返回的结果',
  `gold` int(11) NOT NULL COMMENT '元宝',
  `goods_id` int(11) NOT NULL COMMENT '商品Id',
  `level` int(11) NOT NULL COMMENT '等级',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单号',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `rmb` int(11) NOT NULL COMMENT '订单金额',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '下单时间',
  `transaction_id` varchar(64) DEFAULT NULL COMMENT '第三方支付定单号',
  PRIMARY KEY (`id`),
  KEY `playerId_idx` (`player_id`),
  KEY `summary_idx` (`time`,`server_id`,`channel_id`),
  KEY `orderId_idx` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chop_log`
--

DROP TABLE IF EXISTS `chop_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chop_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `child_id` int(11) NOT NULL COMMENT '碎片id',
  `chop_id` int(11) NOT NULL COMMENT '神器id',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copy_log`
--

DROP TABLE IF EXISTS `copy_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `copy_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `bonus` varchar(1024) DEFAULT NULL COMMENT '奖励内容',
  `copy_id` int(11) NOT NULL COMMENT '副本id',
  `copy_type` int(11) NOT NULL COMMENT '副本类型',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exp_log`
--

DROP TABLE IF EXISTS `exp_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exp_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `expliot_log`
--

DROP TABLE IF EXISTS `expliot_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expliot_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`,`change_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `family_log`
--

DROP TABLE IF EXISTS `family_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `family_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `exp` int(11) NOT NULL COMMENT '工会经验',
  `family_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'familyId',
  `leader_name` varchar(32) NOT NULL COMMENT '会长名字',
  `level` int(11) NOT NULL COMMENT '工会等级',
  `name` varchar(32) NOT NULL COMMENT '工会名称',
  `op_player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作人id',
  `op_player_name` varchar(32) DEFAULT NULL COMMENT '操作人',
  `op_pos` tinyint(4) NOT NULL DEFAULT '0' COMMENT '操作人职位',
  `op_value` tinyint(4) NOT NULL COMMENT '操作类型',
  `target_player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '被操作人id',
  `target_player_name` varchar(32) DEFAULT NULL COMMENT '被操作人',
  `target_pos` tinyint(4) NOT NULL DEFAULT '0' COMMENT '被操作人职位',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`op_player_id`,`target_player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fashion_log`
--

DROP TABLE IF EXISTS `fashion_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fashion_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `fashion_id` int(11) NOT NULL COMMENT '时装id',
  `fashion_level` int(11) NOT NULL COMMENT '等级',
  `hero_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '英雄id',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  `over_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '到期时间',
  `weared` tinyint(4) NOT NULL COMMENT '是否穿戴',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gm_gold_log`
--

DROP TABLE IF EXISTS `gm_gold_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gm_gold_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `count` bigint(20) NOT NULL DEFAULT '0' COMMENT '充值额度',
  `fake_gold` bigint(20) NOT NULL DEFAULT '0' COMMENT '代币',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gold_log`
--

DROP TABLE IF EXISTS `gold_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gold_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`,`change_num`),
  KEY `gold_cost_idx` (`time`,`server_id`,`function_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_log`
--

DROP TABLE IF EXISTS `item_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `bonus_type` varchar(16) NOT NULL COMMENT '物品类型',
  `count` bigint(20) NOT NULL COMMENT '增减数量',
  `current_count` bigint(20) NOT NULL COMMENT '当前数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `item_id` int(11) NOT NULL COMMENT '物品id',
  `item_name` varchar(16) NOT NULL COMMENT '物品名字',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`,`count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jian_log`
--

DROP TABLE IF EXISTS `jian_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jian_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `hero_index` int(11) NOT NULL COMMENT '英雄下标',
  `jianlevel` int(11) NOT NULL COMMENT '飞剑等级',
  `jianstar` int(11) NOT NULL COMMENT '飞剑总星数',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login_log`
--

DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `lastip` varchar(32) NOT NULL COMMENT '上次登录IP',
  `regist_day` tinyint(1) NOT NULL COMMENT '是否当天注册',
  `sys_log` tinyint(1) NOT NULL COMMENT '系统输出的日志',
  PRIMARY KEY (`id`),
  KEY `summary_idx` (`time`,`server_id`,`channel_id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logout_log`
--

DROP TABLE IF EXISTS `logout_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logout_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `lastip` varchar(32) NOT NULL COMMENT '上次登录IP',
  `login_time` bigint(20) NOT NULL COMMENT '登录时间',
  `regist_day` tinyint(1) NOT NULL COMMENT '是否当天注册',
  PRIMARY KEY (`id`),
  KEY `playerId_idx` (`player_id`),
  KEY `logout_server_idx` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lottery_log`
--

DROP TABLE IF EXISTS `lottery_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lottery_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `equip_id` int(11) NOT NULL COMMENT '奖励Id',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mail_log`
--

DROP TABLE IF EXISTS `mail_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mail_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `bonus` varchar(1024) NOT NULL COMMENT '邮件礼包内容',
  `config_id` int(11) DEFAULT '0' COMMENT 'configId',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '邮件创建时间',
  `is_get` tinyint(4) NOT NULL COMMENT '否已经提取附件',
  `mail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '邮件id',
  `mail_type` tinyint(4) NOT NULL COMMENT '邮件类型',
  `message` varchar(512) DEFAULT NULL COMMENT '邮件内容',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  `read_status` tinyint(4) NOT NULL COMMENT '是否以读',
  `title` varchar(256) DEFAULT NULL COMMENT '邮件标题',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `market_log`
--

DROP TABLE IF EXISTS `market_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `market_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `amount` int(11) NOT NULL COMMENT '花费金额',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `count` int(11) NOT NULL COMMENT '数量',
  `currency` int(11) NOT NULL COMMENT '货币类型',
  `item_id` int(11) NOT NULL COMMENT '道具ID',
  `item_name` varchar(32) NOT NULL COMMENT '道具名字',
  `level` int(11) NOT NULL COMMENT '等级',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `price` int(11) NOT NULL COMMENT '物品单价',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `playerId_idx` (`player_id`),
  KEY `statis_idx` (`time`,`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `money_log`
--

DROP TABLE IF EXISTS `money_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `money_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `offline_reward_log`
--

DROP TABLE IF EXISTS `offline_reward_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offline_reward_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `bonus` varchar(1024) NOT NULL COMMENT '奖励内容',
  `gap` bigint(20) NOT NULL DEFAULT '0' COMMENT '离线间隔',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `online_statis_log`
--

DROP TABLE IF EXISTS `online_statis_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `online_statis_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道',
  `count` int(11) NOT NULL COMMENT '在线人数',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '日志时间',
  PRIMARY KEY (`id`),
  KEY `server_time_idx` (`time`,`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `other_resource_log`
--

DROP TABLE IF EXISTS `other_resource_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `other_resource_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `type` int(11) NOT NULL COMMENT '资源类型',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`,`change_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `player_create_log`
--

DROP TABLE IF EXISTS `player_create_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_create_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `summary_idx` (`time`,`server_id`,`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `player_day_info_log`
--

DROP TABLE IF EXISTS `player_day_info_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_day_info_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `day_online_time` int(11) NOT NULL DEFAULT '0' COMMENT '当天在线时间秒',
  `exp` bigint(20) NOT NULL DEFAULT '0' COMMENT '经验',
  `gold` bigint(20) NOT NULL DEFAULT '0' COMMENT '充值币',
  `money` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏币',
  `r_exp` int(11) NOT NULL DEFAULT '0' COMMENT '转生修为',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `player_info_log`
--

DROP TABLE IF EXISTS `player_info_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_info_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `exp` bigint(20) NOT NULL DEFAULT '0' COMMENT '经验',
  `gold` bigint(20) NOT NULL DEFAULT '0' COMMENT '充值币',
  `money` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏币',
  `power` bigint(20) NOT NULL DEFAULT '0' COMMENT '战斗力',
  `r_exp` int(11) NOT NULL DEFAULT '0' COMMENT '转生修为',
  `r_level` int(11) NOT NULL DEFAULT '0' COMMENT '转身等级',
  `vip_exp` int(11) NOT NULL DEFAULT '0' COMMENT 'vip经验',
  `vip_level` int(11) NOT NULL DEFAULT '0' COMMENT 'vip等级',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `player_statis_log`
--

DROP TABLE IF EXISTS `player_statis_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_statis_log` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `daily` int(11) NOT NULL COMMENT '日期',
  `level_map` text COMMENT '平台',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `total` int(11) NOT NULL COMMENT '总人数',
  PRIMARY KEY (`id`),
  KEY `daily_idx` (`daily`,`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pokedex_log`
--

DROP TABLE IF EXISTS `pokedex_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokedex_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `cost` varchar(256) DEFAULT NULL COMMENT '消耗',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  `pokedex_id` int(11) NOT NULL COMMENT '异闻录id',
  `pokedex_level` int(11) NOT NULL COMMENT '异闻录等级',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `practice_log`
--

DROP TABLE IF EXISTS `practice_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `practice_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `child_config_id` int(11) NOT NULL COMMENT '子功能配置id',
  `child_type` tinyint(4) NOT NULL COMMENT '子功能类型',
  `config_id` int(11) NOT NULL COMMENT '配置id',
  `cost` varchar(512) DEFAULT NULL COMMENT '消耗',
  `exp` int(11) NOT NULL COMMENT '经验',
  `hero_index` tinyint(4) NOT NULL COMMENT '英雄下标',
  `level_id` int(11) NOT NULL COMMENT '等级或配置id',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property` (
  `prop_key` varchar(128) NOT NULL COMMENT 'propKey',
  `prop_value` varchar(2048) DEFAULT '' COMMENT 'propValue',
  PRIMARY KEY (`prop_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qi_log`
--

DROP TABLE IF EXISTS `qi_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qi_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rebirth_log`
--

DROP TABLE IF EXISTS `rebirth_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rebirth_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `before_level` int(11) NOT NULL DEFAULT '0' COMMENT '转生前等级',
  `cost` varchar(256) NOT NULL COMMENT '消耗 ',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rexp_log`
--

DROP TABLE IF EXISTS `rexp_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rexp_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `samsara_exp_log`
--

DROP TABLE IF EXISTS `samsara_exp_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `samsara_exp_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `change_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '增减数量',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `samsara_level` int(11) NOT NULL COMMENT '轮回等级',
  `value` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前数量',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `seal_stone_log`
--

DROP TABLE IF EXISTS `seal_stone_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seal_stone_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `equip_id` int(11) NOT NULL COMMENT '装备ID',
  `equip_index` int(11) NOT NULL COMMENT '部位',
  `hero_pos` int(11) NOT NULL COMMENT '英雄槽位',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `serial_gift_log`
--

DROP TABLE IF EXISTS `serial_gift_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serial_gift_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `bonus` varchar(1024) NOT NULL COMMENT '奖励内容',
  `cd_key` varchar(32) NOT NULL COMMENT 'cdKey',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`),
  KEY `cdKey_idx` (`cd_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `server_info_log`
--

DROP TABLE IF EXISTS `server_info_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `server_info_log` (
  `server_id` int(11) NOT NULL DEFAULT '0' COMMENT 'serverId',
  `charge_port` int(11) NOT NULL COMMENT 'chargePort',
  `daily_reset_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '服务器重置时间',
  `gm_port` int(11) NOT NULL COMMENT 'gmPort',
  `ip` varchar(32) NOT NULL DEFAULT '' COMMENT 'ip',
  `last_update_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  `merge_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '合服时间',
  `min_node_open_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '跨服节点中最后开服的时间',
  `open_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '开服时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT 'operatorId',
  `port` int(11) NOT NULL COMMENT '端口',
  PRIMARY KEY (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_log`
--

DROP TABLE IF EXISTS `share_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `share_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  `share_count` int(11) NOT NULL COMMENT '分享次数',
  `share_reward_count` int(11) NOT NULL COMMENT '分享奖励次数',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `title_log`
--

DROP TABLE IF EXISTS `title_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `title_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `expire_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '过期时间',
  `op_value` tinyint(4) NOT NULL COMMENT '操作',
  `title_id` int(11) NOT NULL COMMENT '称号id',
  PRIMARY KEY (`id`),
  KEY `player_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wing_log`
--

DROP TABLE IF EXISTS `wing_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wing_log` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道id',
  `level` int(11) NOT NULL COMMENT '等级',
  `plat_name` varchar(32) NOT NULL COMMENT '平台',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'playerId',
  `player_name` varchar(32) NOT NULL COMMENT '玩家名',
  `server_id` int(11) NOT NULL COMMENT 'serverId',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `function_type` varchar(32) NOT NULL COMMENT '变化类型',
  `hero_index` int(11) NOT NULL COMMENT '英雄下标',
  `winglevel` int(11) NOT NULL COMMENT '翅膀等级',
  `wingstar` int(11) NOT NULL COMMENT '翅膀总星数',
  PRIMARY KEY (`id`),
  KEY `playerId_index` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-24 10:37:08
