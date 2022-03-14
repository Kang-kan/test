package com.xxgame.logsrv.module.collectlog.entity;

import javax.persistence.*;

/**
 * 角色创建日志
 * @author gil
 *
 */
@Entity(name = "player_create_log")
@Table(indexes = @Index(name = "summary_idx", columnList = "time,serverId,channelId"))
public class PlayerCreateLog extends BasePlayerLogEntity {

}
