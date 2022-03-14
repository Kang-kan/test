package com.xxgame.admin.web.modules.customerservice.repository;

import com.xxgame.admin.web.modules.customerservice.entity.BlockPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 封禁角色
 * @author gil
 *
 */
public interface BlockPlayerRepository extends JpaRepository<BlockPlayer, Long> {

    /**
     * 分而查询BlockPlayer
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM blockPlayer t WHERE t.serverId > 0")
    Page<BlockPlayer> findBlockPlayers(Pageable pageable);

    /**
     * 分而查询BlockPlayer
     * @param serverId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM blockPlayer t WHERE t.serverId = ?1")
    Page<BlockPlayer> findBlockPlayers(int serverId, Pageable pageable);

}
