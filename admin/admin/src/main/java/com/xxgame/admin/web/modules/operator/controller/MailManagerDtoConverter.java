package com.xxgame.admin.web.modules.operator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.operator.controller.model.MailPlayerInfoDto;
import com.xxgame.admin.web.modules.operator.controller.model.RewardMailDto;
import com.xxgame.admin.web.modules.operator.entity.RewardMail;
import com.xxgame.admin.web.modules.user.SystemUserService;
import com.xxgame.admin.web.modules.user.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * MailManagerDtoConverter
 */
@Service
public class MailManagerDtoConverter {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 转换成RewardMailDto
     * @param rewardMail
     * @return
     */
    public RewardMailDto toRewardMailDto(RewardMail rewardMail) {
        List<Integer> serverIds = JSON.parseObject(rewardMail.getServerIds(), new TypeReference<List<Integer>>(){});
        List<MailPlayerInfoDto> players = JSON.parseObject(rewardMail.getPlayers(), new TypeReference<List<MailPlayerInfoDto>>(){});

        SystemUser systemUser = systemUserService.getSystemUser(rewardMail.getCreatorId());

        RewardMailDto dto = new RewardMailDto();
        dto.setId(rewardMail.getId());
        dto.setCreator(systemUser.getName());
        dto.setRemark(rewardMail.getRemark());
        dto.setType(rewardMail.getType());
        dto.setServerIds(serverIds);
        dto.setPlayers(players);
        dto.setTitle(rewardMail.getTitle());
        dto.setContent(rewardMail.getContent());
        dto.setRewards(rewardMail.getRewards());
        dto.setSendTime(rewardMail.getStartTime());
        dto.setEndTime(rewardMail.getEndTime());
        dto.setFailSrvIds(rewardMail.getFailSrvIds());

        if (rewardMail.getReviewerId() > 0) {
            systemUser = systemUserService.getSystemUser(rewardMail.getReviewerId());
            dto.setReviewer(systemUser.getName());
        }
        dto.setReviewTime(rewardMail.getReviewTime());

        if (rewardMail.getSenderId() > 0) {
            systemUser = systemUserService.getSystemUser(rewardMail.getSenderId());
            dto.setSender(systemUser.getName());
        }
        dto.setSendTime(rewardMail.getSendTime());

        dto.setCreateTime(rewardMail.getCreateTime());

        return dto;
    }

    /**
     * 转换成RewardMailDto列表
     * @param rewardMails
     * @return
     */
    public List<RewardMailDto> toRewardMailDtos(List<RewardMail> rewardMails) {
        if (rewardMails == null || rewardMails.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<RewardMailDto> dtos = new ArrayList<>(rewardMails.size());
        for (RewardMail rewardMail : rewardMails) {
            dtos.add(this.toRewardMailDto(rewardMail));
        }

        return dtos;
    }

}
