package com.xxgame.admin.web.modules.gamelog.service;

import com.xxgame.admin.web.model.SearchResult;
import com.xxgame.admin.web.modules.gamelog.controller.model.LogSearchRequest;

/**
 * 日志服务类
 */
public interface GameLogService {

    /**
     * 搜索
     * @param logName
     * @param request
     * @return
     */
    SearchResult search(String logName, LogSearchRequest request);
}
