package com.xxgame.admin.web.modules.operator;

import com.xxgame.admin.web.modules.operator.controller.model.RewardMailRequest;
import com.xxgame.admin.web.modules.operator.entity.RewardMail;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 奖励邮件服务类
 */
public interface RewardMailService {

    /**
     * 分而查找奖励邮件
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<RewardMail> listRewardMail(int pageNo, int pageSize);

    /**
     * 分页查找某个帐号的奖励邮件
     * @param creatorId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<RewardMail> findRewardMails(long creatorId, int pageNo, int pageSize);

    /**
     * 创建奖励邮件
     * @param creatorId
     * @param request
     * @return
     */
    RewardMail createRewardMail(long creatorId, RewardMailRequest request);

    /**
     * 修改奖励邮件
     * @param mail
     * @param request
     * @return
     */
    RewardMail updateRewardMail(RewardMail mail, RewardMailRequest request);

    /**
     * 发送全服奖励邮件
     * @param senderId
     * @param rewardMail
     * @return
     */
    List<Integer> sendSrvRewardMail(long senderId, RewardMail rewardMail);
}
