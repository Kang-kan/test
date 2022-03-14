package com.xxgame.admin.web.modules.customerservice.repository;

import com.xxgame.admin.web.modules.customerservice.entity.CustomerService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 客服
 * @author gil
 *
 */
public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long> {

    /**
     * 分页查找留言
     * @param status
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM customer_service t WHERE t.status = ?1 ORDER BY id DESC")
    Page<CustomerService> findMessage(int status, Pageable pageable);

    /**
     * 分页查找留言
     * @param serverId
     * @param channelId
     * @param status
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM customer_service t WHERE t.serverId >= ?1 AND t.channelId <= ?2 AND t.status = ?3 ORDER BY id DESC")
    Page<CustomerService> findMessage(int serverId, String channelId, int status, Pageable pageable);

    /**
     * 分页查找留言
     * @param serverId
     * @param status
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM customer_service t WHERE t.serverId >= ?1 AND t.status = ?2 ORDER BY id DESC")
    Page<CustomerService> findMessage(int serverId, int status, Pageable pageable);
    
    /**
     * 查找玩家留言
     * @param playerId
     * @return
     */
    @Query("SELECT t FROM customer_service t WHERE t.playerId = ?1 ORDER BY id DESC")
    List<CustomerService> findPlayerMessages(long playerId);
}
