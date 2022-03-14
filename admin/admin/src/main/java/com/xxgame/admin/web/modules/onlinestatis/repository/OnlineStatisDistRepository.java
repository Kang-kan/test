package com.xxgame.admin.web.modules.onlinestatis.repository;

import com.xxgame.admin.web.modules.onlinestatis.entity.OnlineStatisDist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 在线时长分布
 * @author gil
 *
 */
public interface OnlineStatisDistRepository extends JpaRepository<OnlineStatisDist, String> {

    /**
     * 在线时长分布
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM online_statis_dist t WHERE t.daily >= ?1 AND t.daily <= ?2")
    Page<OnlineStatisDist> findOnlineDist(int startTime, int endTime, Pageable pageable);

    /**
     * 在线时长分布
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM online_statis_dist t WHERE t.daily >= ?1 AND t.daily <= ?2 AND t.serverId = ?3")
    Page<OnlineStatisDist> findOnlineDist(int startTime, int endTime, int serverId, Pageable pageable);

}
