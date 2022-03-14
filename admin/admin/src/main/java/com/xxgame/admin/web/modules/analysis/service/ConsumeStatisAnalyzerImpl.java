package com.xxgame.admin.web.modules.analysis.service;

import com.xxgame.admin.web.modules.commons.logdao.GoldLogDao;
import com.xxgame.admin.web.modules.commons.logdao.MarketLogDao;
import com.xxgame.admin.web.modules.consumestatis.entity.GoldConsumeStatis;
import com.xxgame.admin.web.modules.consumestatis.entity.MarketStatis;
import com.xxgame.admin.web.modules.consumestatis.repository.ConsumeStatisRepository;
import com.xxgame.admin.web.modules.consumestatis.repository.GoldConsumeStatisRepository;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 商城消耗统计
 */
@Service
public class ConsumeStatisAnalyzerImpl implements ConsumeStatisAnalyzer {

    @Autowired
    private MarketLogDao marketLogDao;
    @Autowired
    private GoldLogDao goldLogDao;
    @Autowired
    private ConsumeStatisRepository consumeStatisRepository;
    @Autowired
    private GoldConsumeStatisRepository goldConsumeStatisRepository;
    @Autowired
    private GameServerRepository gameServerRepository;

    @Override
    public void analyzeMarketStatis(Date date) {
        Date today0Am = DateUtils.dayAm0(date);
        Date last0Am = DateUtils.lastDayAm0(date);
        String daily = DateUtils.date2String(last0Am, DateUtils.PATTERN_YYYYMMDD);

        List<GameServer> gameServers =  gameServerRepository.findOpenServers(System.currentTimeMillis());
        for (GameServer gameServer : gameServers) {
            int serverId = gameServer.getId();
            int totalPlayerCount = marketLogDao.analyzeMarketPlayerCount(last0Am.getTime(), today0Am.getTime(), serverId);
            List<Map<String, Object>> rowMaps = marketLogDao.analyzeMarketStatis(last0Am.getTime(), today0Am.getTime(), serverId);

            Map<String, MarketStatis> resultMaps = new HashMap<>();

            int totalTimes = 0;
            int totalAmount = 0;
            for (Map<String, Object> row : rowMaps) {
                int itemId = (int) row.get("item_id");
                String itemName = (String) row.get("item_name");
                int currency = (int) row.get("currency");
                int count = ((Number) row.get("count")).intValue();
                int amount = ((Number) row.get("amount")).intValue();
                int times = ((Number) row.get("times")).intValue();
                int playerCount = ((Number) row.get("playerCount")).intValue();

                totalTimes += times;
                totalAmount += amount;

                MarketStatis marketStatis = new MarketStatis();
                marketStatis.setId(daily + serverId + itemId);
                marketStatis.setServerId(serverId);
                marketStatis.setDaily(Integer.parseInt(daily));
                marketStatis.setItemId(itemId);
                marketStatis.setItemCount(count);
                marketStatis.setItemName(itemName);
                marketStatis.setPlayerCount(playerCount);
                marketStatis.setTimes(times);
                marketStatis.setCurrency(currency);
                marketStatis.setAmount(amount);

                resultMaps.put(marketStatis.getId(), marketStatis);
            }

            // 保存入库
            for (MarketStatis marketStatis : resultMaps.values()) {
                marketStatis.setTotalPlayerCount(totalPlayerCount);
                marketStatis.setTotalTimes(totalTimes);
                marketStatis.setTotalAmount(totalAmount);
                consumeStatisRepository.save(marketStatis);
            }
        }
    }

    @Override
    public void analyzeGoldStatis(Date date) {
        Date today0Am = DateUtils.dayAm0(date);
        Date last0Am = DateUtils.lastDayAm0(date);
        String daily = DateUtils.date2String(last0Am, DateUtils.PATTERN_YYYYMMDD);

        List<GameServer> gameServers =  gameServerRepository.findOpenServers(System.currentTimeMillis());
        for (GameServer gameServer : gameServers) {
            int serverId = gameServer.getId();

            int totalPlayerCount = goldLogDao.analyzeGoldPlayerCount(last0Am.getTime(), today0Am.getTime(), serverId);
            List<Map<String, Object>> rowMaps = goldLogDao.analyzeGoldStatis(last0Am.getTime(), today0Am.getTime(), serverId);

            Map<String, GoldConsumeStatis> resultMaps = new HashMap<>();

            for (Map<String, Object> row : rowMaps) {
                String functionType = (String) row.get("function_type");
                int count = ((Number) row.get("count")).intValue();
                int changeNum = ((Number) row.get("changeNum")).intValue();

                GoldConsumeStatis goldConsumeStatis = new GoldConsumeStatis();
                goldConsumeStatis.setId(daily + serverId + functionType);
                goldConsumeStatis.setServerId(serverId);
                goldConsumeStatis.setDaily(Integer.parseInt(daily));
                goldConsumeStatis.setFunctionType(functionType);
                goldConsumeStatis.setFunctionCount(count);
                goldConsumeStatis.setGold(changeNum);

                resultMaps.put(goldConsumeStatis.getId(), goldConsumeStatis);
            }

            // 保存入库
            for (GoldConsumeStatis goldConsumeStatis : resultMaps.values()) {
                goldConsumeStatis.setTotal(totalPlayerCount);
                goldConsumeStatisRepository.save(goldConsumeStatis);
            }
        }
    }

}
