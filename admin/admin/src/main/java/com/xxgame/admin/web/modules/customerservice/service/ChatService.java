package com.xxgame.admin.web.modules.customerservice.service;

import com.xxgame.admin.web.model.SearchResult;
import com.xxgame.admin.web.modules.customerservice.controller.model.ChatLogSearch;

import java.util.Set;

/**
 * 聊天管理
 */
public interface ChatService {

    /**
     * 获取聊天屏蔽词库
     * @return
     */
    Set<String> getBlockWords();

    /**
     * 添加屏蔽词
     * @param blockWord
     */
    boolean addBlockWord(String blockWord);

    /**
     * 删除屏蔽词
     * @param blockWord
     * @return
     */
    boolean removeBlockWord(String blockWord);

    /**
     * 搜索
     * @param search
     * @return
     */
    SearchResult search(ChatLogSearch search);

    /**
     * 滚动显示聊天记录
     * @param search
     * @return
     */
    SearchResult scroll(ChatLogSearch search);

}
