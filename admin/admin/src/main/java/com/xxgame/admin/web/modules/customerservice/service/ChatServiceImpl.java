package com.xxgame.admin.web.modules.customerservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.model.*;
import com.xxgame.admin.web.modules.customerservice.controller.model.ChatLogSearch;
import com.xxgame.admin.web.modules.customerservice.entity.ChatBlockWord;
import com.xxgame.admin.web.modules.customerservice.repository.ChatBlockWordRepository;
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

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 聊天管理
 */
@Service
public class ChatServiceImpl implements ChatService {

    /**
     * 搜索服url
     */
    @Value("${social.search.chatLog.url}")
    private String searchUrl;

    private final String indexName = "chatLog";

    @Autowired
    private ChatBlockWordRepository chatBlockWordRepository;
    @Autowired
    private RestTemplate restTemplate;

    private final int ENTITY_ID = 1;

    private LinkedHashSet<String> blockWords;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void init() {
        ChatBlockWord blockWord = null;
        Optional<ChatBlockWord> optional = chatBlockWordRepository.findById(ENTITY_ID);
        if (!optional.isPresent()) {
            blockWord = new ChatBlockWord();
            blockWord.setId(ENTITY_ID);
            blockWord.setBlockWords("[]");
            blockWord.setUpdateTime(System.currentTimeMillis());
            chatBlockWordRepository.save(blockWord);
        } else {
            blockWord = optional.get();
        }
        blockWords = JSON.parseObject(blockWord.getBlockWords(), new TypeReference<LinkedHashSet<String>>(){});
    }

    @Override
    public Set<String> getBlockWords() {
        return blockWords;
    }

    @Override
    public boolean addBlockWord(String blockWord) {
        boolean added = blockWords.add(blockWord);
        if (!added) {
            return false;
        }

        ChatBlockWord chatBlockWord = chatBlockWordRepository.getOne(ENTITY_ID);
        chatBlockWord.setBlockWords(JSON.toJSONString(blockWords));
        chatBlockWordRepository.save(chatBlockWord);

        return true;
    }

    @Override
    public boolean removeBlockWord(String blockWord) {
        boolean exist = blockWords.remove(blockWord);
        if (!exist) {
            return false;
        }

        ChatBlockWord chatBlockWord = chatBlockWordRepository.getOne(ENTITY_ID);
        chatBlockWord.setBlockWords(JSON.toJSONString(blockWords));
        chatBlockWordRepository.save(chatBlockWord);

        return true;
    }

    @HystrixCommand(fallbackMethod = "searchFallback", commandKey = "search")
    @Override
    public SearchResult search(ChatLogSearch search) {
        SearchArgs searchArgs = buildSearchArgs(search);

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

    @HystrixCommand(fallbackMethod = "searchFallback", commandKey = "search")
    @Override
    public SearchResult scroll(ChatLogSearch search) {
        long now = System.currentTimeMillis();
        long startTime = Math.min(search.getScrollTime(), now);
        long endTime = Math.max(search.getScrollTime(), now);
        List<Object> range = Lists.newArrayList(startTime, endTime);

        Map<String, List<Object>> rangeMap = new HashMap<>(1);
        rangeMap.put("time", range);

        SearchArgs searchArgs = buildSearchArgs(search);
        searchArgs.setRange(rangeMap);

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
     * @param search
     * @return
     */
    private SearchArgs buildSearchArgs(ChatLogSearch search) {
        SearchArgs searchArgs = new SearchArgs();
        searchArgs.setIndexName(indexName);
        searchArgs.setKeyWord(search.getKeyWord());
        searchArgs.setFrom(search.getPageNo() * search.getPageSize());
        searchArgs.setFetchSize(search.getPageSize());

        List<QueryArgs> musts = new ArrayList<>();
        searchArgs.setMust(musts);

        if (search.getServerId() > 0) {
            musts.add(QueryArgs.valueOf("serverId", search.getServerId()));
        }
        if (StringUtils.isNotBlank(search.getAccount())) {
            musts.add(QueryArgs.valueOf("account", search.getAccount()));
        }
        if (StringUtils.isNotBlank(search.getChannelId())) {
            musts.add(QueryArgs.valueOf("channelId", search.getChannelId()));
        }
        if (StringUtils.isNotBlank(search.getPlayerId())) {
            musts.add(QueryArgs.valueOf("playerId", search.getPlayerId()));
        }
        if (StringUtils.isNotBlank(search.getPlayerName())) {
            musts.add(QueryArgs.valueOf("match", "playerName", search.getPlayerName()));
        }
        if (search.getChatChannel() != null) {
            musts.add(QueryArgs.valueOf("chatChannel", search.getChatChannel()));
        }

        List<Pair> order = new ArrayList<>();
        order.add(Pair.valueOf("time", "DESC"));
        searchArgs.setOrder(order);
        
        return searchArgs;
    }

    public SearchResult searchFallback(ChatLogSearch search) {
        logger.error("请求搜索网络异常，参数:{}", JSON.toJSONString(search));
        return null;
    }

}
