package com.xxgame.admin.web.modules.commons.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏日志
 */
@Service
public class QueryGameServiceImpl implements QueryGameService {

    @Autowired
    private GmClient gmClient;
    @Autowired
    private GameServerService gameServerService;

    @Override
    public long toPlayerId(int serverId, String playerId, String accountId, String playerName) {
        if (StringUtils.isNotBlank(playerId)) {
            return Long.valueOf(playerId).longValue();
        } else {
            return this.queryPlayerId(serverId, accountId, playerName);
        }
    }

    @Override
    public long queryPlayerId(int serverId, String accountId, String playerName) {
        String gameAccount = this.buildAccountId(serverId, accountId);

        String resultStr = gmClient.queryPlayerIds(serverId, gameAccount, playerName);
        if (StringUtils.isBlank(resultStr)) {
            return 0;
        }

        JSONArray resultArray = JSON.parseArray(resultStr);
        if (resultArray == null || resultArray.isEmpty()) {
            return 0;
        }

        return (Long) resultArray.get(0);
    }

    @Override
    public JSONArray queryPlayerInfos(int serverId, String playerId, String accountId, String playerName) {
        Map<String, String> parameter = new HashMap<>(1);
        if (StringUtils.isNotBlank(playerId)) {
            parameter.put("playerId", playerId);
        } else if (StringUtils.isNotBlank(accountId)) {
            String gameAccount = this.buildAccountId(serverId, accountId);
            parameter.put("userId", gameAccount);
        } else {
            parameter.put("playerName", playerName);
        }

        String resultStr = gmClient.getPlayerInfo(serverId, parameter);
        if (StringUtils.isBlank(resultStr)) {
            return null;
        }

        return JSON.parseArray(resultStr);
    }

    @Override
    public long toFamilyId(int serverId, String familyId, String familyName) {
		if (StringUtils.isNotBlank(familyId)) {
            return Long.valueOf(familyId).longValue();
		} else {
			return this.queryFamilyId(serverId, familyName);
		}
    }

    /**
     * 组装成游戏服账号id
     * @param serverId
     * @param accountId
     * @return
     */
    private String buildAccountId(int serverId, String accountId) {
        if (StringUtils.isBlank(accountId)) {
            return accountId;
        }

        if (accountId.split("\\.").length == 3) {
            return accountId;
        }

        GameServer gameServer = gameServerService.getCacheGameServer(serverId);
        return String.format("%s.%s.%s", accountId, gameServer.getOperatorId(), serverId);
    }

    @Override
    public long queryFamilyId(int serverId, String familyName) {
        String resultStr = null;
        if (StringUtils.isNotBlank(familyName)) {
            resultStr = gmClient.findFamilyInfo(serverId, null, familyName);
        }
        if (StringUtils.isBlank(resultStr)) {
            return 0;
        }

        JSONArray resultArray = JSON.parseArray(resultStr);
        if (resultArray == null || resultArray.isEmpty()) {
            return 0;
        }
        JSONObject jsonObject = (JSONObject) resultArray.get(0);

        return jsonObject.getLongValue("id");
    }

}
