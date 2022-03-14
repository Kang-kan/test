package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色创建日志
 */
@Service
public class PlayerCreateLogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 分析月活跃汇总
     * @param date
     * @return
     */
    public List<Map<String, Object>> analyzeMonthlyActive(Date date) {
        String sql = "SELECT server_id,channel_id,COUNT(1) AS count " +
                "FROM player_create_log " +
                "WHERE `time` >= ? AND `time` < ? " +
                "GROUP BY server_id,channel_id";
        Date lastMonth = DateUtils.lastMonthAm0(date);
        Date thisMonth = DateUtils.monthAm0(date);

        return jdbcTemplate.queryForList(sql, lastMonth.getTime(), thisMonth.getTime());
    }

    /**
     * 日常数据统计-注册统计
     * @param date
     * @return
     */
    public List<Map<String, Object>> analyzeDailyStatisRegist(Date date) {
        String sql = "SELECT server_id,channel_id,COUNT(player_id) AS count " +
                "FROM player_create_log " +
                "WHERE `time` >= ? AND `time` < ? " +
                "GROUP BY server_id,channel_id";

        Date lastDate = DateUtils.lastDayAm0(date);
        Date today = DateUtils.dayAm0(date);
        return jdbcTemplate.queryForList(sql, lastDate.getTime(), today.getTime());
    }

    /**
     * 时间段注册人数
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public int registCount(int serverId, long startTime, long endTime) {
        String sql = "SELECT COUNT(*) FROM player_create_log WHERE time >= ? AND time <= ? AND server_id = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startTime, endTime, serverId);
        return count == null ? 0 : count;
    }

    /**
     * 时间段注册人数
     * @param serverId
     * @param channelId
     * @param startTime
     * @param endTime
     * @return
     */
    public int registCount(int serverId, String channelId, long startTime, long endTime) {
        String sql = "SELECT COUNT(*) FROM player_create_log WHERE time >= ? AND time <= ? AND server_id = ? AND channel_id = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startTime, endTime, serverId, channelId);
        return count == null ? 0 : count;
    }

    /**
     * 每日注册统计
     * @param date
     * @param serverId
     * @return
     */
    public List<Map<String, Object>> analyzeDailyStatisRegist(Date date, int serverId) {
        String sql = "SELECT server_id,channel_id,COUNT(player_id) AS count " +
                "FROM player_create_log " +
                "WHERE `time` >= ? AND `time` < ? AND server_id = ? " +
                "GROUP BY server_id,channel_id";

        Date lastDate = DateUtils.lastDayAm0(date);
        Date today = DateUtils.dayAm0(date);
        return jdbcTemplate.queryForList(sql, lastDate.getTime(), today.getTime(), serverId);
    }

    /**
     * 每日注册用户id
     * @param date
     * @param serverId
     * @param channelId
     * @return
     */
    public List<Map<String, Object>> dailyCreatePlayerIds(Date date, int serverId, String channelId) {
        String sql = "SELECT player_id " +
                "FROM player_create_log " +
                "WHERE `time` >= ? AND `time` < ? AND server_id = ? AND channel_id = ?";

        Date startDate = DateUtils.dayAm0(date);
        Date endDate = DateUtils.nextDayAm0(startDate);
        return jdbcTemplate.queryForList(sql, startDate.getTime(), endDate.getTime(), serverId, channelId);
    }

    /**
     * 某个时间段内注册的玩家id列表
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> createPlayerIds(int serverId, long startTime, long endTime) {
        String sql = "SELECT player_id " +
                "FROM player_create_log " +
                "WHERE `time` >= ? AND `time` < ? AND server_id = ?";

        return jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
    }

}
