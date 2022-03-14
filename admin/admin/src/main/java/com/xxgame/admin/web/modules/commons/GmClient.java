package com.xxgame.admin.web.modules.commons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GmClient
 */
@Component
public class GmClient {

    /**
     * gmkey
     */
    @Value("${social.gmKey}")
    private String gmKey;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Autowired
    private GameServerService gameServerService;

    private final String MODULE_ID = "admin";

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 请求游戏服查询sql
     * @param serverId
     * @param sqlKey
     * @param queryType 0-翻页查询，1-查询总数
     * @param startIndex
     * @param queryCount 0-获取所有
     * @param args sql语句的参数
     * @return
     */
    public String querySqlForGame(int serverId, String sqlKey, int queryType, int startIndex, int queryCount, Object... args) {
        int cmd = GmCmd.COMMON_QUERY;
        String signMD5 = sign(cmd);

        Map<String, String> params = new HashMap<>();
        params.put("cmd", cmd + "");
        params.put("commonParams", JSONObject.toJSONString(args));
        params.put("sign", signMD5);
        params.put("moduleId", MODULE_ID);
        params.put("sqlKey", sqlKey);
        params.put("queryType", queryType + "");
        params.put("startIndex", startIndex + "");
        params.put("queryCount", queryCount + "");

        String url = getRequestUrl(serverId);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriBuilder.build().encode().toUri();
        return getRestTemplate().getForObject(uri, String.class);
    }

    /**
     * 发送玩家邮件
     * @param serverId
     * @param mailParams
     * @return
     */
    public String sendPlayerMail(int serverId, Map<String, String> mailParams) {
        int cmd = GmCmd.PLAYERMAIL_SEND;

        String result = sendRequest(serverId, cmd, mailParams);
        logger.info("请求游戏服发送邮件serverId：{} params：{} 返回值：{}", serverId, JSON.toJSONString(mailParams), result);

        return result;
    }

    /**
     * 发送全服邮件
     * @param serverId
     * @param title
     * @param content
     * @return
     */
    public ListenableFuture<ResponseEntity<String>> sendSystemMail(int serverId, String title, String content) {
        int cmd = GmCmd.SYSTEMMAIL_SEND;
        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("mailTitle", title);
        parameter.put("mailContent", content);

        return sendAsyncRequest(serverId, cmd, parameter);
    }

    /**
     * 获取玩家信息
     * @param serverId
     * @param parameter
     * @return
     */
    public String getPlayerInfo(int serverId, Map<String, String> parameter) {
        int cmd = GmCmd.GET_PLAYER_INFO;

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 查找玩家id
     * @param serverId
     * @param accountId
     * @param playerName
     * @return
     */
    public String queryPlayerIds(int serverId, String accountId, String playerName) {
        int cmd = GmCmd.QUERY_PLAYER_ID;

        Map<String, String> parameter = new HashMap<>(1);
        if (StringUtils.isNotBlank(accountId)) {
            parameter.put("userId", accountId);
        } else {
            parameter.put("playerName", playerName);
        }

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 查找仙盟信息
     * @param serverId
     * @param familyId
     * @param familyName
     * @return
     */
    public String findFamilyInfo(int serverId, String familyId, String familyName) {
        int cmd = GmCmd.FIND_FAMILY;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("familyId", familyId);
        parameter.put("familyName", familyName);

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 分页查找玩家列表
     * @param serverId
     * @param startIndex
     * @param queryCount
     * @return
     */
    public String listPlayers(int serverId, int startIndex, int queryCount) {
        int cmd = GmCmd.PLAYER_LIST;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("startIndex", startIndex + "");
        parameter.put("queryCount", queryCount + "");

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 封号
     * @param serverId
     * @param playerId
     * @param unBlockTime
     * @param reason
     * @return
     */
    public String blockPlayer(int serverId, String playerId, long unBlockTime, String reason) {
        int cmd = GmCmd.PLAYER_BAN;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("playerId", playerId);
        parameter.put("banPlayerTime", unBlockTime + "");
        parameter.put("banPlayerReason", reason);

        String result = sendRequest(serverId, cmd, parameter);
        logger.info("请求游戏服封号 serverId：{} params：{} 返回值：{}", serverId, JSON.toJSONString(parameter), result);

        return result;
    }

    /**
     * 禁言
     * @param serverId
     * @param playerId
     * @param unBlockTime
     * @param reason
     * @return
     */
    public String blockChat(int serverId, String playerId, long unBlockTime, String reason) {
        int cmd = GmCmd.WORDS_BAN;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("playerId", playerId);
        parameter.put("banWordTime", unBlockTime + "");
        parameter.put("banWordReason", reason);

        String result = sendRequest(serverId, cmd, parameter);
        logger.info("请求游戏服禁言 serverId：{} params：{} 返回值：{}", serverId, JSON.toJSONString(parameter), result);

        return result;
    }

    /**
     * 获取排行榜
     * @param serverId
     * @param rankType
     * @return
     */
    public String queryRank(int serverId, int rankType) {
        int cmd = GmCmd.QUERY_RANK;

        Map<String, String> parameter = new HashMap<>(1);
        parameter.put("rankType", rankType + "");

        String result = sendRequest(serverId, cmd, parameter);

        return result;
    }

    /**
     * 查询玩家道具
     * @param serverId
     * @param playerId
     * @param itemId
     * @return
     */
    public String queryPlayerItems(int serverId, long playerId, Integer itemId) {
        int cmd = GmCmd.LIST_PLAYER_ITEM;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("playerId", playerId + "");
        parameter.put("itemId", itemId == null ? null : itemId.toString());

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 查询玩家装备
     * @param serverId
     * @param playerId
     * @param equipId
     * @return
     */
    public String queryPlayerEquips(int serverId, long playerId, Integer equipId) {
        int cmd = GmCmd.LIST_PLAYER_EQUIP;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("playerId", playerId + "");
        parameter.put("equipId", equipId == null ? null : equipId.toString());

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 分页查找仙盟列表
     * @param serverId
     * @param startIndex
     * @param queryCount
     * @return
     */
    public String listFamilys(int serverId, int startIndex, int queryCount) {
        int cmd = GmCmd.LIST_FAMILY;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("startIndex", startIndex + "");
        parameter.put("queryCount", queryCount + "");

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 設置開服時間
     * @param serverId
     * @return
     */
    public String setOpenTime(int serverId, long time) {
        int cmd = GmCmd.SETSERVER_OPEN_TIME;

        Map<String, String> parameter = new HashMap<>(1);
        parameter.put("time", String.valueOf(time));

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 修改仙盟公告
     * @param serverId
     * @param familyId
     * @param notice
     * @return
     */
    public String updateFamilyNotice(int serverId, String familyId, String notice) {
        int cmd = GmCmd.UPDATE_FAMILY_NOTICE;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("familyId", familyId);
        parameter.put("noticeContent", notice);

        return sendRequest(serverId, cmd, parameter);
    }

    /**
     * 查询充值排名
     * @param serverId
     * @return
     */
    public String queryChargeRank(int serverId) {
        int cmd = GmCmd.CHARGE_RANK;

        Map<String, String> parameter = new HashMap<>(2);
        parameter.put("startIndex", "0");
        parameter.put("queryCount", "10");

        String result = sendRequest(serverId, cmd, parameter);

        return result;
    }

    /**
     * 发送全服奖励邮件
     * @param serverId
     * @param parameter
     * @return
     */
    public ListenableFuture<ResponseEntity<String>> sendSrvRewardMail(int serverId, Map<String, String> parameter) {
        int cmd = GmCmd.GIFTS_SEND;

        return sendAsyncRequest(serverId, cmd, parameter);
    }

    /**
     * 发送跑灯公告
     * @param serverId
     * @param content
     * @return
     */
    public ListenableFuture<ResponseEntity<String>> sendChatNotice(int serverId, List<String> channels, String content) {
        int cmd = GmCmd.NOTICE_SEND;
        Map<String, String> parameter = new HashMap<>(1);
        parameter.put("noticeContent", content);
        parameter.put("channels", JSON.toJSONString(channels));

        return sendAsyncRequest(serverId, cmd, parameter);
    }

    /**
     * 重新加载数值配置表
     * @param serverId
     * @return
     */
    public ListenableFuture<ResponseEntity<String>> reloadBaseData(int serverId) {
        int cmd = GmCmd.RELOAD_DATA;
        Map<String, String> parameter = new HashMap<>(1);

        return sendAsyncRequest(serverId, cmd, parameter);
    }

    /**
     * 通知游戏服同步屏蔽字库
     * @param serverId
     * @return
     */
    public ListenableFuture<ResponseEntity<String>> syncBlockWords(int serverId) {
        int cmd = GmCmd.SYNC_BLOCK_WORDS;
        Map<String, String> parameter = new HashMap<>(1);

        return sendAsyncRequest(serverId, cmd, parameter);
    }

    /**
     * 发送GM请求
     * @param serverId
     * @param cmd
     * @param parameter
     * @return
     */
    private String sendRequest(int serverId, int cmd, Map<String, String> parameter) {
        String signMD5 = sign(cmd);

        Map<String, String> params = new HashMap<>();
        params.put("cmd", cmd + "");
        params.put("sign", signMD5);
        params.put("moduleId", MODULE_ID);
        params.putAll(parameter);

        String url = getRequestUrl(serverId);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriBuilder.build().encode().toUri();

        return getRestTemplate().getForObject(uri, String.class);
    }

    /**
     * 发送异步GM请求
     * @param serverId
     * @param cmd
     * @param parameter
     * @return
     */
    private ListenableFuture<ResponseEntity<String>> sendAsyncRequest(int serverId, int cmd, Map<String, String> parameter) {
        String signMD5 = sign(cmd);

        Map<String, String> params = new HashMap<>();
        params.put("cmd", cmd + "");
        params.put("sign", signMD5);
        params.put("moduleId", MODULE_ID);
        params.putAll(parameter);

        String url = getRequestUrl(serverId);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriBuilder.build().encode().toUri();

        return new AsyncRestTemplate().getForEntity(uri, String.class);
    }

    /**
     * 签名
     * @param cmd
     * @return
     */
    private String sign(int cmd) {
        StringBuilder sb = new StringBuilder();
        sb.append(gmKey).append(cmd).append(gmKey).append(MODULE_ID);
        return DigestUtils.md5Hex(sb.toString());
    }

    /**
     * 获取请求url
     * @param serverId
     * @return
     */
    private String getRequestUrl(int serverId) {
        GameServer gameServer = gameServerService.getCacheGameServer(serverId);
        return String.format("http://%s:%s", gameServer.getLocalIp(), gameServer.getGmPort());
    }

    /**
     * 获取RestTemplate
     * @return
     */
    private RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(3000);
        return new RestTemplate(requestFactory);
    }

}
