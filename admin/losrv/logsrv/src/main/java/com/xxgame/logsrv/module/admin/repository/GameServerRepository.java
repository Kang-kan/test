package com.xxgame.logsrv.module.admin.repository;

import com.xxgame.logsrv.module.admin.entity.GameServer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 游戏服配置
 * @author gil
 *
 */
public interface GameServerRepository extends JpaRepository<GameServer, Integer> {

 }
