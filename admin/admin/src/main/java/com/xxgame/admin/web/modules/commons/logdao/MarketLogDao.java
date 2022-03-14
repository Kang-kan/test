package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商城日志
 */
@Service
public class MarketLogDao extends AbstractDao {

    /**
     * 商城消耗统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @return
     */
    public List<Map<String, Object>> analyzeMarketStatis(long startTime, long endTime, int serverId) {
        String sql = "SELECT item_id, item_name, currency, " +
                "SUM(count) AS count, SUM(amount) AS amount, " +
                "COUNT(player_id) AS times, COUNT(DISTINCT player_id) AS playerCount " +
                "FROM market_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? " +
                "GROUP BY item_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
    }

    /**
     * 商城消耗总购买人数
     * @param startTime
     * @param endTime
     * @param serverId
     * @return
     */
    public int analyzeMarketPlayerCount(long startTime, long endTime, int serverId) {
        String sql = "SELECT COUNT(DISTINCT player_id) AS playerCount " +
                "FROM market_log " +
                "WHERE time >= ? AND time < ? AND server_id = ?";

        List<Map<String, Object>> rowMaps = jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
        if (rowMaps == null || rowMaps.isEmpty()) {
            return 0;
        }
        Long playerCount = (Long) rowMaps.get(0).get("playerCount");
        return playerCount.intValue();
    }

    /**
     * 分页查找玩家商城日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM market_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM market_log WHERE player_id = ? ORDER BY id DESC";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家商城日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM market_log WHERE player_id = ? AND `time` >= ? AND `time` < ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM market_log WHERE player_id = ? AND `time` >= ? AND `time` < ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家商城日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param itemId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int itemId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM market_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND item_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM market_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND item_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, itemId);
    }

    /**
     * 根据道具id查找商城日志
     * @param serverId
     * @param itemId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findTiemLog(int serverId, int itemId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM market_log WHERE server_id = ? AND item_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM market_log WHERE server_id = ? AND item_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, serverId, itemId);
    }

    /**
     * 根据道具id查找商城日志
     * @param serverId
     * @param itemId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findTiemLog(int serverId, int itemId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM market_log WHERE `time` >= ? AND `time` < ? AND server_id = ? AND item_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM market_log WHERE `time` >= ? AND `time` < ? AND server_id = ? AND item_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, startTime, endTime, serverId, itemId);
    }

}
