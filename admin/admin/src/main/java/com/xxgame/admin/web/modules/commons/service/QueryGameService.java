package com.xxgame.admin.web.modules.commons.service;

import com.alibaba.fastjson.JSONArray;

/**
 * 查找游戏服
 */
public interface QueryGameService {

    /**
     * 根据传参找到对应用玩家id。playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。
     * @param serverId
     * @param playerId
     * @param accountId
     * @param playerName
     * @return
     */
    long toPlayerId(int serverId, String playerId, String accountId, String playerName);

    /**
     * 根据账号名或用户名查找玩家id
     * @param serverId
     * @param accountId
     * @param playerName
     * @return
     */
    long queryPlayerId(int serverId, String accountId, String playerName);

    /**
     * 查找玩家信息
     * @param serverId
     * @param playerId
     * @param accountId
     * @param playerName
     * @return
     */
    JSONArray queryPlayerInfos(int serverId, String playerId, String accountId, String playerName);

    /**
	 * 根据传参找到对应用仙盟id。amilyId、familyName 二个参数传其中1个就好，按先后顺序优先使用。
	 * @param serverId
	 * @param familyId
	 * @param familyName
	 * @return
	 */
	long toFamilyId(int serverId, String familyId, String familyName);

    /**
     * 查找仙盟id
     * @param serverId
     * @param familyName
     * @return
     */
    long queryFamilyId(int serverId, String familyName);
}
