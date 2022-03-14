package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * LoginLogDao
 */
@Service
public class LoginLogDao extends AbstractDao {

    /**
     * 分析月活跃汇总
     * @param date
     * @return
     */
    public List<Map<String, Object>> analyzeMonthlyActive(Date date) {
        String sql = "SELECT server_id,channel_id,COUNT(distinct player_id) AS count " +
                "FROM login_log " +
                "WHERE `time` >= ? AND `time` < ? " +
                "GROUP BY server_id,channel_id";
        Date lastMonth = DateUtils.lastMonthAm0(date);
        Date thisMonth = DateUtils.monthAm0(date);
        return jdbcTemplate.queryForList(sql, lastMonth.getTime(), thisMonth.getTime());
    }

    /**
     * 查找某时间段的login人数，按天分组
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> loginCount(int serverId, long startTime, long endTime) {
        String sql = "SELECT channel_id, COUNT(DISTINCT player_id) AS count, FROM_UNIXTIME(time DIV 1000, '%Y-%m-%d') AS loginDate " +
                "FROM login_log WHERE time >= ? AND time <= ? AND server_id = ? " +
                "GROUP BY loginDate, channel_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
    }

    /**
     * 某时间段内的login人数
     * @param serverId
     * @param channelId
     * @param startTime
     * @param endTime
     * @return
     */
    public int loginCount(int serverId, String channelId, long startTime, long endTime) {
        String sql = "SELECT COUNT(DISTINCT player_id) " +
                "FROM login_log " +
                "WHERE time >= ? AND time <= ? AND server_id = ? AND channel_id = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startTime, endTime, serverId, channelId);
        return count == null ? 0 : count;
    }

    /**
     * 日常数据统计-登录人数
     * @param date
     * @return
     */
    public List<Map<String, Object>> analyzeDailyStatisLogin(Date date) {
        String sql = "SELECT server_id,channel_id,COUNT(DISTINCT player_id) AS count " +
                "FROM login_log " +
                "WHERE `time` >= ? AND `time` < ? " +
                "GROUP BY server_id,channel_id";

        Date lastDate = DateUtils.lastDayAm0(date);
        Date today = DateUtils.dayAm0(date);
        return jdbcTemplate.queryForList(sql, lastDate.getTime(), today.getTime());
    }

    /**
     * 每小时数据统计-登录人数
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> analyzeHourStatisLogin(long startTime, long endTime) {
        String sql = "SELECT server_id,COUNT(DISTINCT player_id) AS count " +
                "FROM login_log " +
                "WHERE `time` >= ? AND `time` < ? " +
                "GROUP BY server_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime);
    }

    /**
     * 每小时数据统计-渠道登录人数
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> analyzeChannelHourStatisLogin(long startTime, long endTime) {
        String sql = "SELECT server_id,COUNT(DISTINCT player_id) AS count, channel_id " +
                "FROM login_log " +
                "WHERE `time` >= ? AND `time` < ? " +
                "GROUP BY server_id,channel_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime);
    }

    /**
     * 分页查找玩家登录日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM login_log WHERE player_id = ? AND sys_log != TRUE ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM login_log WHERE player_id = ? AND sys_log != TRUE";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 某时间段内这些人有没登录过
     * @param serverId
     * @param channelId
     * @param startTime
     * @param endTime
     * @param playerIds
     * @return
     */
    public int loginCount(int serverId, String channelId, long startTime, long endTime, Set<Long> playerIds) {
        String sql = "SELECT COUNT(DISTINCT player_id) AS count " +
                "FROM login_log " +
                "WHERE time >= :startTime AND time < :endTime AND server_id = :serverId AND channel_id = :channelId AND player_id IN(:playerIds)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("serverId", serverId);
        paramMap.put("channelId", channelId);
        paramMap.put("playerIds", playerIds);

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        Integer count = jdbc.queryForObject(sql, paramMap, Integer.class);

        return count == null ? 0 : count;
    }

    /**
     * 某时间段内这些人有没登录过
     * @param serverId
     * @param startTime
     * @param endTime
     * @param playerIds
     * @return
     */
    public int loginCount(int serverId, long startTime, long endTime, Collection<Long> playerIds) {
        String sql = "SELECT COUNT(DISTINCT player_id) AS count " +
                "FROM login_log " +
                "WHERE time >= :startTime AND time < :endTime AND server_id = :serverId AND player_id IN(:playerIds)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("serverId", serverId);
        paramMap.put("playerIds", playerIds);

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        Integer count = jdbc.queryForObject(sql, paramMap, Integer.class);

        return count == null ? 0 : count;
    }

}
