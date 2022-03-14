package com.xxgame.admin.web.modules.customerservice.service;

import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.commons.IdWorkerService;
import com.xxgame.admin.web.modules.customerservice.MessageStatus;
import com.xxgame.admin.web.modules.customerservice.controller.model.MessageRequest;
import com.xxgame.admin.web.modules.customerservice.entity.CustomerService;
import com.xxgame.admin.web.modules.customerservice.repository.CustomerServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 客服
 */
@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {

    @Autowired
    private CustomerServiceRepository customerServiceRepository;
    @Autowired
    private IdWorkerService idWorkerService;
    @Autowired
    private GmClient gmClient;

    @Override
    public Result<CustomerService> replyMessage(long userId, long messageId, String replyTitle, String replyText) {
        CustomerService customerService = customerServiceRepository.getOne(messageId);
        if (customerService == null) {
            return Result.error(ResultCode.PARAM_ERROR, "找不到留言记录");
        }
        // 发送邮件
        Map<String, String> mailParams = new HashMap<>();
        mailParams.put("playerId", customerService.getPlayerId() + "");
        mailParams.put("mailTitle", replyTitle);
        mailParams.put("mailContent", replyText);
        mailParams.put("channels", "");

        gmClient.sendPlayerMail(customerService.getServerId(), mailParams);

        customerService.setStatus(MessageStatus.REPLY.getValue());
        customerService.setReplier(userId);
        customerService.setReply(replyText);
        customerService.setReplyTime(new Date());

        customerService = customerServiceRepository.save(customerService);
        return Result.success(customerService);
    }

    @Override
    public CustomerService leaveMessage(MessageRequest request, String ip) {
        // 发送邮件
        Map<String, String> mailParams = new HashMap<>();
        mailParams.put("playerId", request.getPlayerId() + "");
        mailParams.put("mailTitle", "反馈邮件");
        mailParams.put("mailContent", "亲爱的玩家您好，您的反馈已收到！我们将尽快与您联系！");
        mailParams.put("channels", "");

        gmClient.sendPlayerMail(request.getServerId(), mailParams);

        CustomerService customerService = new CustomerService();
        customerService.setId(idWorkerService.nextCommonId());
        customerService.setServerId(request.getServerId());
        customerService.setChannelId(request.getChannelId());
        customerService.setPlayerId(Long.valueOf(request.getPlayerId()));
        customerService.setPlayerName(request.getPlayerName());
        customerService.setLevel(request.getLevel());
        customerService.setVipLevel(request.getVipLevel());
        customerService.setIp(ip);
        customerService.setContext(request.getContext());

        return customerServiceRepository.save(customerService);
    }

}
