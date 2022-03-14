package com.xxgame.admin.web.modules.operator;

import com.xxgame.admin.web.modules.operator.controller.model.ChatNoticeRequest;
import com.xxgame.admin.web.modules.operator.entity.ChatNotice;
import com.xxgame.admin.web.modules.operator.model.ChatNoticeContext;

import java.util.List;

/**
 * 跑马灯公告
 */
public interface ChatNoticeService {

    /**
     * 获取全部跑马灯公告
     * @return
     */
    List<ChatNotice> getAllChatNotices();

    /**
     * 增加跑马灯公告
     * @param requestUserId
     * @param request
     * @return
     */
    ChatNotice addChatNotice(long requestUserId, ChatNoticeRequest request);

    /**
     * 修改跑马灯公告
     * @param requestUserId
     * @param chatNoticeId
     * @param request
     * @return
     */
    ChatNotice updateChatNotice(long requestUserId, long chatNoticeId, ChatNoticeRequest request);

    /**
     * 删除跑马灯公告
     * @param chatNoticeId
     */
    void deleteChatNotice(long chatNoticeId);

    /**
     * 执行发送公告
     * @param request
     */
    List<Integer> sendNotice(ChatNoticeContext request);

}
