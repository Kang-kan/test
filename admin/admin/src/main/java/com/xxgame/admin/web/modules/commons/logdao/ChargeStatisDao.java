package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值统计
 */
@Service
public class ChargeStatisDao extends AbstractDao {

    /**
     * 充值总数相关
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, Object> chargeTotalStatis(String channelId, int serverId, long startTime, long endTime) {
        String sql = "SELECT COUNT(*) AS totalCount, SUM(rmb) totalAmount, COUNT(DISTINCT player_id) AS totalPlayer " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0";
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };
        List<Map<String, Object>> rowMaps = jdbcTemplate.queryForList(sql, args);
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new HashMap<>(0);
        }

        return rowMaps.get(0);
    }

    /**
     * 每日统计充值统计-每服、每个渠道、充值人数
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> chargeDailyStatis(long startTime, long endTime) {
        String sql = "SELECT server_id, channel_id, COUNT(DISTINCT player_id) AS count " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND arrival_time > 0 " +
                "GROUP BY server_id, channel_id";
        Object[] args = new Object[]{ startTime, endTime };

        return jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 查询充值玩家id
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> chargePlayers(String channelId, int serverId, long startTime, long endTime) {
        String sql = "SELECT DISTINCT player_id " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id =? AND channel_id =? AND arrival_time > 0 ";
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };

        return jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 分页查找单笔充值分布
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> singleChargeDist(String channelId, int serverId, long startTime, long endTime, int pageNo, int pageSize) {
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };

        String sql = "SELECT COUNT(rmb) AS count, rmb, channel_id " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0 " +
                "GROUP BY rmb";
        String countSql = "SELECT COUNT(*) FROM (SELECT COUNT(rmb) FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0 " +
                "GROUP BY rmb) AS tt";

        return this.queryPage(sql, countSql, pageNo, pageSize, args);
    }

    /**
     * 查找单笔充值分布
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> singleChargeDist(String channelId, int serverId, long startTime, long endTime) {
        String sql = "SELECT COUNT(rmb) AS count, rmb, channel_id " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0 " +
                "GROUP BY rmb";
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };

        return jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 查找单笔充值分布
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> singleChargeDist(int serverId, long startTime, long endTime) {
        String sql = "SELECT COUNT(rmb) AS count, rmb, channel_id " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND arrival_time > 0 " +
                "GROUP BY rmb, channel_id";
        Object[] args = new Object[]{ startTime, endTime, serverId };

        return jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 查找累计充值分布
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> totalChargeDist(String channelId, int serverId, long startTime, long endTime) {
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };

        String sql = "SELECT player_id, SUM(rmb) AS playerAmount " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0 " +
                "GROUP BY player_id";

        return this.jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 首充等级分布
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> firstChargeLevelDist(String channelId, int serverId, long startTime, long endTime) {
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };

        String sql = "SELECT level, COUNT(distinct player_id) AS count " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0 AND first_rmb > 0 " +
                "GROUP BY level";

        return this.jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 付费等级分布
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> chargeLevelDist(String channelId, int serverId, long startTime, long endTime) {
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };

        String sql = "SELECT level, COUNT(distinct player_id) AS count, SUM(rmb) AS amount " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0 " +
                "GROUP BY level";

        return this.jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 元宝消耗排行
     * @param channelId
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> costRank(String channelId, int serverId, long startTime, long endTime) {
        Object[] args = new Object[]{ startTime, endTime, serverId, channelId };

        String sql = "SELECT player_id, player_name, COUNT(player_id) AS times, SUM(change_num) AS amount " +
                "FROM gold_log WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND change_num < 0 " +
                "GROUP BY player_id " +
                "ORDER BY amount ASC " +
                "LIMIT 100";

        return this.jdbcTemplate.queryForList(sql, args);
    }

}
