package com.xxgame.admin.web.modules.gamelog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.model.*;
import com.xxgame.admin.web.modules.gamelog.controller.model.LogSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 日志服务类
 */
@Service
public class GameLogServiceImpl implements GameLogService {

    /**
     * 搜索服url
     */
    @Value("${social.search.gameLog.url}")
    private String searchUrl;
    @Autowired
    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @HystrixCommand(fallbackMethod = "searchFallback", commandKey = "search")
    @Override
    public SearchResult search(String logName, LogSearchRequest request) {
        SearchArgs searchArgs = buildSearchArgs(logName, request);

        String body = JSON.toJSONString(searchArgs);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        String resStr = restTemplate.postForObject(searchUrl, entity, String.class);
        if (StringUtils.isBlank(resStr)) {
            throw new BusinessException(ResultCode.FAIL, "网络异常，请稍后再试。");
        }

        return JSON.parseObject(resStr, new TypeReference<SearchResult>(){});
    }

    /**
     * 组装搜索参数
     * @param logName
     * @param request
     * @return
     */
    private SearchArgs buildSearchArgs(String logName, LogSearchRequest request) {
        SearchArgs searchArgs = new SearchArgs();
        searchArgs.setIndexName(logName);
        searchArgs.setFrom(request.getPageNo() * request.getPageSize());
        searchArgs.setFetchSize(request.getPageSize());

        List<QueryArgs> musts = new ArrayList<>();
        searchArgs.setMust(musts);

        if (request.getServerId() > 0) {
            musts.add(QueryArgs.valueOf("serverId", request.getServerId()));
        }
        if (StringUtils.isNotBlank(request.getAccount())) {
            musts.add(QueryArgs.valueOf("account", request.getAccount()));
        }
        if (StringUtils.isNotBlank(request.getChannelId())) {
            musts.add(QueryArgs.valueOf("channelId", request.getChannelId()));
        }
        if (StringUtils.isNotBlank(request.getPlayerId())) {
            musts.add(QueryArgs.valueOf("playerId", request.getPlayerId()));
        }
        if (StringUtils.isNotBlank(request.getPlayerName())) {
            musts.add(QueryArgs.valueOf("match", "playerName", request.getPlayerName()));
        }

        List<Pair> order = new ArrayList<>();
        order.add(Pair.valueOf("time", "DESC"));
        searchArgs.setOrder(order);

        return searchArgs;
    }

    public SearchResult searchFallback(String logName, LogSearchRequest request) {
        logger.error("请求搜索网络异常，参数:{}", JSON.toJSONString(request));
        return null;
    }

}
