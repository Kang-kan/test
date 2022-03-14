package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消耗统计
 */
@Service
public class GoldLogDao extends AbstractDao {

    /**
     * 元宝消耗统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @return
     */
    public List<Map<String, Object>> analyzeGoldStatis(long startTime, long endTime, int serverId) {
        String sql = "SELECT COUNT(DISTINCT player_id) AS count, function_type, SUM(change_num) AS changeNum " +
                "FROM gold_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND change_num < 0 " +
                "GROUP BY function_type";

        return jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
    }

    /**
     * 元宝消耗总购买人数
     * @param startTime
     * @param endTime
     * @param serverId
     * @return
     */
    public int analyzeGoldPlayerCount(long startTime, long endTime, int serverId) {
        String sql = "SELECT COUNT(DISTINCT player_id) AS playerCount " +
                "FROM gold_log " +
                "WHERE time >= ? AND time < ? AND server_id = ?";

        List<Map<String, Object>> rowMaps = jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
        if (rowMaps == null || rowMaps.isEmpty()) {
            return 0;
        }
        Long playerCount = (Long) rowMaps.get(0).get("playerCount");
        return playerCount.intValue();
    }

    /**
     * 时间段消耗元宝
     * @param serverId
     * @param channelId
     * @param startTime
     * @param endTime
     * @return
     */
    public int consumeAmount(int serverId, String channelId, long startTime, long endTime) {
        String sql = "SELECT SUM(change_num) AS consumeGold " +
                "FROM gold_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND change_num < 0";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startTime, endTime, serverId, channelId);
        return count == null ? 0 : count;
    }

    /**
     * 分页查找玩家金币日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家金币日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND time >= ? AND time < ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家金币日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param fuctionType
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, String fuctionType, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND function_type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND function_type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, fuctionType);
    }

    /**
     * 分页查找玩家金币消费日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND change_num < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND change_num < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家金币消费日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家金币消费日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param fuctionType
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, long startTime, long endTime, String fuctionType, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0 AND function_type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0 AND function_type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, fuctionType);
    }

    /**
     * 分页查找玩家金币产出日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND change_num > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND change_num > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家金币产出日志
     * @param playerId
     * @param startTime
     * @param fuctionType
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, long startTime, long endTime, String fuctionType, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0 function_type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0 function_type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, fuctionType);
    }

    /**
     * 分页查找玩家金币产出日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gold_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

}
