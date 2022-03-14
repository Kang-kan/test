package com.xxgame.admin.web.modules.operator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.commons.IdWorkerService;
import com.xxgame.admin.web.modules.operator.controller.model.MailPlayerInfoDto;
import com.xxgame.admin.web.modules.operator.controller.model.RewardMailRequest;
import com.xxgame.admin.web.modules.operator.entity.RewardMail;
import com.xxgame.admin.web.modules.operator.model.MailExtraParam;
import com.xxgame.admin.web.modules.operator.repository.RewardMailRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 奖励邮件
 */
@Service
public class RewardMailServiceImpl implements RewardMailService {

    @Autowired
    private RewardMailRepository rewardMailRepository;
    @Autowired
    private IdWorkerService idWorkerService;
    @Autowired
    private GmClient gmClient;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page<RewardMail> listRewardMail(int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return rewardMailRepository.findAll(pageRequest);
    }

    @Override
    public Page<RewardMail> findRewardMails(long creatorId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return rewardMailRepository.findRewardMails(creatorId, pageRequest);
    }

    @Override
    public RewardMail createRewardMail(long creatorId, RewardMailRequest request) {
        long id = idWorkerService.nextShortId();
        RewardMail mail = new RewardMail();
        mail.setId(id);
        mail.setCreatorId(creatorId);
        mail.setRemark(request.getRemark());
        mail.setType(request.getType());
        mail.setServerIds(JSON.toJSONString(request.getServerIds()));
        mail.setPlayers(JSON.toJSONString(request.getPlayers()));
        mail.setTitle(request.getTitle());
        mail.setContent(request.getContent());
        mail.setRewards(request.getRewards());
        mail.setStartTime(request.getStartTime());
        mail.setEndTime(request.getEndTime());
        mail.setCreateTime(System.currentTimeMillis());

        return rewardMailRepository.save(mail);
    }

    @Override
    public RewardMail updateRewardMail(RewardMail mail, RewardMailRequest request) {
        mail.setRemark(request.getRemark());
        mail.setType(request.getType());
        mail.setServerIds(JSON.toJSONString(request.getServerIds()));
        mail.setPlayers(JSON.toJSONString(request.getPlayers()));
        mail.setTitle(request.getTitle());
        mail.setContent(request.getContent());
        mail.setRewards(request.getRewards());
        mail.setStartTime(request.getStartTime());
        mail.setEndTime(request.getEndTime());

        return rewardMailRepository.save(mail);
    }

    @Override
    public List<Integer> sendSrvRewardMail(long senderId, RewardMail rewardMail) {
        long now = System.currentTimeMillis();
        Set<Long> playerIds = new HashSet<>();
        if (StringUtils.isNotBlank(rewardMail.getPlayers())) {
            List<MailPlayerInfoDto> players = JSON.parseObject(rewardMail.getPlayers(), new TypeReference<List<MailPlayerInfoDto>>(){});
            for (MailPlayerInfoDto info : players) {
                playerIds.add(info.getPlayerId());
            }
        }

        // 组装参数
        Map<String, String> parameter = new HashMap<>();
        parameter.put("mailTitle", rewardMail.getTitle());
        parameter.put("mailContent", rewardMail.getContent());
        parameter.put("startTime", rewardMail.getStartTime() + "");
        parameter.put("endTime", rewardMail.getEndTime() + "");
        parameter.put("channels", "[]");
        parameter.put("giftId", rewardMail.getId() + "");
        parameter.put("giftType", rewardMail.getType() + ""); // 1-全服邮件
        parameter.put("rewards", rewardMail.getRewards());
        if (!playerIds.isEmpty()) {
            parameter.put("playerIds", JSON.toJSONString(playerIds));
        }
        parameter.put("extraParam", JSON.toJSONString(new MailExtraParam()));

        LinkedHashMap<Integer, ListenableFuture<ResponseEntity<String>>> futures = new LinkedHashMap<>();
        List<Integer> failSrvIds = new ArrayList<>();

        List<Integer> serverIds = JSON.parseObject(rewardMail.getServerIds(), new TypeReference<List<Integer>>(){});
        for (int serverId : serverIds) {
            ListenableFuture<ResponseEntity<String>> future = gmClient.sendSrvRewardMail(serverId, parameter);
            futures.put(serverId, future);
        }

        for (Map.Entry<Integer, ListenableFuture<ResponseEntity<String>>> entry : futures.entrySet()) {
            int serverId = entry.getKey();
            try {
                ResponseEntity<String> responseEntity = entry.getValue().get(3, TimeUnit.SECONDS);
                if (responseEntity.getStatusCode() != HttpStatus.OK) {
                    failSrvIds.add(serverId);
                    logger.info("发送全服奖励邮件失败，serverId:{} content:{}", serverId, JSON.toJSONString(parameter));
                    continue;
                }
                String resultStr = responseEntity.getBody();
                if (resultStr == null) {
                    failSrvIds.add(serverId);
                    logger.info("发送全服奖励邮件超时，serverId:{} content:{}", serverId, JSON.toJSONString(parameter));
                    continue;
                }

                Map<String, Integer> resultMap = JSON.parseObject(resultStr, new TypeReference<Map<String, Integer>>(){});
                if (resultMap.get("resultCode") != 0) {
                    failSrvIds.add(serverId);
                    logger.info("发送全服奖励邮件返回失败，serverId:{} content:{} result:{}", serverId, JSON.toJSONString(parameter), resultStr);
                    continue;
                }
            } catch (Exception e) {
                failSrvIds.add(serverId);
                String msg = String.format("发送全服奖励邮件异常，serverId:%s content:%s", serverId, JSON.toJSONString(parameter));
                logger.info(msg, e);
            }
        }

        // 保存发送状态
        rewardMail.setSenderId(senderId);
        rewardMail.setSendTime(now);
        rewardMail.setFailSrvIds(JSON.toJSONString(serverIds));
        rewardMailRepository.save(rewardMail);

        return failSrvIds;
    }

}
