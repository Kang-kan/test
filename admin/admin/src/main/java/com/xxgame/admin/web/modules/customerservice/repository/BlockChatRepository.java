package com.xxgame.admin.web.modules.customerservice.repository;

import com.xxgame.admin.web.modules.customerservice.entity.BlockChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 禁言
 * @author gil
 *
 */
public interface BlockChatRepository extends JpaRepository<BlockChat, Long> {

    /**
     * 分而查询BlockChat
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM blockChat t WHERE t.serverId > 0")
    Page<BlockChat> findBlockChats(Pageable pageable);

    /**
     * 分而查询BlockChat
     * @param serverId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM blockChat t WHERE t.serverId = ?1")
    Page<BlockChat> findBlockChats(int serverId, Pageable pageable);

}
