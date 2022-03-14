package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值定单
 */
@Service
public class ChargeOrderLogDao extends AbstractDao {

    /**
     * 每日各渠道充值统计
     * @param date
     * @return
     */
    public List<Map<String, Object>> analyzeChannelDailyCharge(Date date) {
        String sql = "SELECT server_id,channel_id,SUM(rmb) AS rmb " +
                "FROM charge_order_log " +
                "WHERE `time` >= ? AND `time` < ? AND arrival_time > 0 " +
                "GROUP BY server_id, channel_id";

        Date lastDate = DateUtils.lastDayAm0(date);
        Date today = DateUtils.dayAm0(date);
        return jdbcTemplate.queryForList(sql, lastDate.getTime(), today.getTime());
    }

    /**
     * 日常数据统计-充值统计
     * @param date
     * @return
     */
    public List<Map<String, Object>> analyzeDailyStatisCharge(Date date) {
        String sql = "SELECT server_id,channel_id,COUNT(player_id) AS chargeCount,COUNT(DISTINCT player_id) " +
                "AS chargePlayer,SUM(rmb) AS chargeAmout,SUM(first_rmb) AS todyAmout " +
                "FROM charge_order_log " +
                "WHERE `time` >= ? AND `time` < ? AND arrival_time > 0 " +
                "GROUP BY server_id, channel_id";

        Date lastDate = DateUtils.lastDayAm0(date);
        Date today = DateUtils.dayAm0(date);
        return jdbcTemplate.queryForList(sql, lastDate.getTime(), today.getTime());
    }

    /**
     * 获取充值人次、充值人数、充值金额、充值元宝
     * @param serverId
     * @param channelId
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, Object> getChargeStatis(int serverId, String channelId, long startTime, long endTime) {
        String sql = "SELECT COUNT(player_id) AS chargeCount, COUNT(DISTINCT player_id) AS chargePlayer, SUM(rmb) AS amount, SUM(gold) AS chargeGold " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0";

        List<Map<String, Object>> rowMaps = jdbcTemplate.queryForList(sql, startTime, endTime, serverId, channelId);
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new HashMap<>(0);
        }
        return rowMaps.get(0);
    }

    /**
     * 时间段首充人数
     * @param serverId
     * @param channelId
     * @param startTime
     * @param endTime
     * @return
     */
    public int firstChargeCount(int serverId, String channelId, long startTime, long endTime) {
        String sql = "SELECT COUNT(player_id) AS firstCharge " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND channel_id = ? AND arrival_time > 0 AND first_rmb > 0";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startTime, endTime, serverId, channelId);
        return count == null ? 0 : count;
    }

    /**
     * 每日统计充值人次、充值人数、充值金额
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> dailyChargeStatis(long startTime, long endTime) {
        String sql = "SELECT server_id, channel_id, COUNT(player_id) AS chargeCount, " +
                "  COUNT(DISTINCT player_id) AS chargePlayer, SUM(rmb) AS amount " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND arrival_time > 0 " +
                "GROUP BY server_id, channel_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime);
    }

    /**
     * 统计新用户每日点击次数、点击人数
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> dailyNewClickStatis(long startTime, long endTime) {
        String sql = "SELECT channel_id, server_id, COUNT(player_id) AS clickCount, COUNT(DISTINCT player_id) AS clickPlayer " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND create_day = TRUE " +
                "GROUP BY server_id, channel_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime);
    }

    /**
     * 统计新用户每日充值
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> dailyNewChargeStatis(long startTime, long endTime) {
        String sql = "SELECT server_id, channel_id, COUNT(player_id) AS chargeCount, " +
                "  COUNT(DISTINCT player_id) AS chargePlayer, SUM(rmb) AS amount " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND arrival_time > 0 AND create_day = TRUE " +
                "GROUP BY server_id, channel_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime);
    }

    /**
     * 统计老用户每日充值
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> dailyOldChargeStatis(long startTime, long endTime) {
        String sql = "SELECT server_id, channel_id, COUNT(player_id) AS chargeCount, " +
                "  COUNT(DISTINCT player_id) AS chargePlayer, SUM(rmb) AS amount " +
                "FROM charge_order_log " +
                "WHERE time >= ? AND time < ? AND arrival_time > 0 AND create_day = FALSE " +
                "GROUP BY server_id, channel_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime);
    }

    /**
     * 根据游戏定单id查找
     * @param orderId
     * @return
     */
    public Map<String, Object> findByOrderId(long orderId) {
        String sql = "SELECT * FROM charge_order_log WHERE order_id = ? ORDER BY id DESC";
        List<Map<String, Object>> rowMaps = jdbcTemplate.queryForList(sql, orderId);
        if (rowMaps == null || rowMaps.isEmpty()) {
            return new HashMap<>(0);
        }
        return rowMaps.get(0);
    }

    /**
     * 分页查找充值日志
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLogs(int pageNo, int pageSize) {
        String sql = "SELECT * FROM charge_order_log ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM charge_order_log";
        return this.queryPage(sql, countSql, pageNo, pageSize);
    }

    /**
     * 分页查找充值日志
     * @param serverId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLogs(int serverId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM charge_order_log WHERE time > 0 AND server_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM charge_order_log WHERE time > 0 AND server_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, serverId);
    }

    /**
     * 分页查找玩家充值日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM charge_order_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM charge_order_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 统计总充值金额
     * @param playerId
     * @return
     */
    public int playerTotalCharge(long playerId) {
        String sql = "SELECT SUM(rmb) FROM charge_order_log WHERE player_id = ? AND arrival_time > 0";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, playerId);
        return count == null ? 0 : count;
    }

    /**
     * 导出充值日志
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> exportLog(long startTime, long endTime) {
        String sql = "SELECT * FROM charge_order_log WHERE `time` >= ? AND `time` < ? AND arrival_time > 0";
        return jdbcTemplate.queryForList(sql, startTime, endTime);
    }

}
