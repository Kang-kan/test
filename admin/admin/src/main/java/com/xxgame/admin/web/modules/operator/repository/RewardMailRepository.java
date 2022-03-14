package com.xxgame.admin.web.modules.operator.repository;

import com.xxgame.admin.web.modules.operator.entity.RewardMail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 奖励邮件
 * @author gil
 *
 */
public interface RewardMailRepository extends JpaRepository<RewardMail, Long> {

    /**
     * 分页查找某个帐号的奖励邮件
     * @param creatorId
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM reward_mail t WHERE t.creatorId = ?1")
    Page<RewardMail> findRewardMails(long creatorId, Pageable pageable);
}
