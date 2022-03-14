package com.xxgame.admin.web.modules.customerservice.service;

import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.modules.customerservice.controller.model.MessageRequest;
import com.xxgame.admin.web.modules.customerservice.entity.CustomerService;

/**
 * 客服
 */
public interface CustomerServiceService {

    /**
     * 回复留言
     * @param userId
     * @param messageId
     * @param replyTitle
     * @param replyText
     * @return
     */
    Result<CustomerService> replyMessage(long userId, long messageId, String replyTitle, String replyText);

    /**
     * 留言
     * @param request
     * @param ip
     * @return
     */
    CustomerService leaveMessage(MessageRequest request, String ip);

}
