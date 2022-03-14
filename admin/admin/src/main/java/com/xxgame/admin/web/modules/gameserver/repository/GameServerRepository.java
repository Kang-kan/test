package com.xxgame.admin.web.modules.gameserver.repository;

import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 游戏服配置
 * @author gil
 *
 */
public interface GameServerRepository extends JpaRepository<GameServer, Integer> {

    /**
     * 查找已开服的服务器列表
     * @param now
     * @return
     */
    @Query(value = "SELECT * FROM game_server t WHERE t.open_time > 0 AND t.open_time <= ?1", nativeQuery = true)
    List<GameServer> findOpenServers(long now);

    /**
     * 查找已开服的服务器列表
     * @param now
     * @return
     */
    @Query(value = "SELECT * FROM game_server t WHERE t.open_time > 0 AND t.open_time <= ?1", nativeQuery = true)
    Page<GameServer> findOpenServers(long now, Pageable pageable);

    /**
     * 查找已开服的服务器列表
     * @param now
     * @param type
     * @return
     */
    @Query(value = "SELECT * FROM game_server t WHERE t.open_time > 0 AND t.open_time <= ?1 AND t.type = ?2", nativeQuery = true)
    Page<GameServer> findOpenServersByType(long now, int type, Pageable pageable);

    /**
     * 时间段开服列表
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT * FROM game_server t WHERE t.open_time >= ?1 AND t.open_time < ?2", nativeQuery = true)
    List<GameServer> findOpenServers(long startTime, long endTime);


}
