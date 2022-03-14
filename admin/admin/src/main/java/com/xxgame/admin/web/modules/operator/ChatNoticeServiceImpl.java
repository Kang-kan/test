package com.xxgame.admin.web.modules.operator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.commons.IdWorkerService;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.operator.controller.model.ChatNoticeRequest;
import com.xxgame.admin.web.modules.operator.entity.ChatNotice;
import com.xxgame.admin.web.modules.operator.model.ChatNoticeContext;
import com.xxgame.admin.web.modules.operator.repository.ChatNoticeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 跑马灯公告
 */
@Service
public class ChatNoticeServiceImpl implements ChatNoticeService {

    @Autowired
    private ChatNoticeRepository chatNoticeRepository;
    @Autowired
    private GameServerService gameServerService;
    @Autowired
    private IdWorkerService idWorkerService;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private GmClient gmClient;

    /**
     * 循环公告定时器
     */
    private final ConcurrentMap<Long, ScheduledFuture<?>> schedulerMap = new ConcurrentHashMap<>();

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        List<ChatNotice> chatNotices = chatNoticeRepository.findAll();
        for (ChatNotice chatNotice : chatNotices) {
            // 免得后台一起服就一下子发很多公告
            long delay = new Random().nextInt(60) * 1000L;
            this.addToScheduler(chatNotice, new Date(System.currentTimeMillis() + delay));
        }
    }

    @Override
    public List<ChatNotice> getAllChatNotices() {
        return chatNoticeRepository.findAll();
    }

    @Override
    public ChatNotice addChatNotice(long requestUserId, ChatNoticeRequest request) {
        ChatNotice chatNotice = new ChatNotice();
        chatNotice.setId(idWorkerService.nextShortId());
        chatNotice.setServerIds(JSON.toJSONString(request.getServerIds()));
        chatNotice.setChannels(JSON.toJSONString(request.getChannels()));
        chatNotice.setNotice(request.getNotice());
        chatNotice.setIntervalTime(request.getIntervalTime());
        chatNotice.setUpdaterId(requestUserId);
        chatNotice.setUpdateTime(System.currentTimeMillis());

        chatNotice = chatNoticeRepository.save(chatNotice);

        // 增加到定时器
        this.addToScheduler(chatNotice, new Date());

        return chatNotice;
    }

    @Override
    public ChatNotice updateChatNotice(long requestUserId, long chatNoticeId, ChatNoticeRequest request) {
        ChatNotice chatNotice = new ChatNotice();
        chatNotice.setId(chatNoticeId);
        chatNotice.setServerIds(JSON.toJSONString(request.getServerIds()));
        chatNotice.setChannels(JSON.toJSONString(request.getChannels()));
        chatNotice.setNotice(request.getNotice());
        chatNotice.setIntervalTime(request.getIntervalTime());
        chatNotice.setUpdaterId(requestUserId);
        chatNotice.setUpdateTime(System.currentTimeMillis());

        chatNotice = chatNoticeRepository.save(chatNotice);

        // 增加到定时器
        this.addToScheduler(chatNotice, new Date());

        return chatNotice;
    }

    @Override
    public void deleteChatNotice(long chatNoticeId) {
        chatNoticeRepository.deleteById(chatNoticeId);
        this.removeScheduler(chatNoticeId);
    }

    /**
     * 执行发送公告
     * @param noticeContext
     */
    @Override
    public List<Integer> sendNotice(ChatNoticeContext noticeContext) {
        List<Integer> fails = new ArrayList<>();

        List<Integer> serverIds = noticeContext.getServerIds();
        // 是否是全服邮件
        if (serverIds == null || serverIds.isEmpty()) {
            serverIds = gameServerService.getAllOpenedServerIds();
        }
        serverIds = gameServerService.runningServerIds(serverIds);

        LinkedHashMap<Integer, ListenableFuture<ResponseEntity<String>>> futures = new LinkedHashMap<>();
        for (int serverId : serverIds) {
            ListenableFuture<ResponseEntity<String>> future = gmClient.sendChatNotice(serverId, noticeContext.getChannels(), noticeContext.getNotice());
            futures.put(serverId, future);
        }

        for (Map.Entry<Integer, ListenableFuture<ResponseEntity<String>>> entry : futures.entrySet()) {
            int serverId = entry.getKey();
            try {
                ResponseEntity<String> responseEntity = entry.getValue().get(3, TimeUnit.SECONDS);
                if (responseEntity.getStatusCode() != HttpStatus.OK) {
                    fails.add(serverId);
                    logger.info("发送跑马灯公告失败，serverId:{} content:{}", serverId, noticeContext.getNotice());
                    continue;
                }
                String resultStr = responseEntity.getBody();
                if (resultStr == null) {
                    fails.add(serverId);
                    logger.info("发送跑马灯公告超时，serverId:{} content:{}", serverId, noticeContext.getNotice());
                    continue;
                }

                Map<String, Integer> resultMap = JSON.parseObject(resultStr, new TypeReference<Map<String, Integer>>(){});
                if (resultMap.get("resultCode") != 0) {
                    fails.add(serverId);
                    logger.info("发送跑马灯公告返回失败，serverId:{} content:{} result:{}", serverId, noticeContext.getNotice(), resultStr);
                    continue;
                }
            } catch (Exception e) {
                fails.add(serverId);
                String msg = String.format("发送跑马灯公告异常，serverId:%s content:%s", serverId, noticeContext.getNotice());
                logger.info(msg, e);
            }
        }

        return fails;
    }

    /**
     * 增加到定时器
     * @param chatNotice
     */
    private void addToScheduler(ChatNotice chatNotice, Date startTime) {
        ScheduledFuture<?> scheduler = schedulerMap.remove(chatNotice.getId());
        if (scheduler != null) {
            scheduler.cancel(true);
        }

        final ChatNoticeContext context = new ChatNoticeContext();
        context.setServerIds(JSON.parseObject(chatNotice.getServerIds(), new TypeReference<List<Integer>>(){}));
        context.setChannels(JSON.parseObject(chatNotice.getChannels(), new TypeReference<List<String>>(){}));
        context.setNotice(chatNotice.getNotice());

        // 定时器
        scheduler = threadPoolTaskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendNotice(context);
            }
        }, startTime, chatNotice.getIntervalTime() * 1000L);

        ScheduledFuture<?> exist = schedulerMap.putIfAbsent(chatNotice.getId(), scheduler);
        // 如果有旧的存在就取消旧的
        if (exist != null) {
            exist.cancel(true);
        }
    }

    /**
     * 取消定时器
     * @param chatNoticeId
     */
    private void removeScheduler(long chatNoticeId) {
        ScheduledFuture<?> scheduler = schedulerMap.remove(chatNoticeId);
        if (scheduler != null) {
            scheduler.cancel(true);
        }
    }

}
