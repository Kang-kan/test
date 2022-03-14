package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * LogoutLogDao
 */
@Service
public class LogoutLogDao extends AbstractDao {

    /**
     * 分页查找玩家登出日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM logout_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM logout_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 查询在线时长分布
     * @param serverId
     * @return
     */
    public List<Map<String, Object>> queryOnlineTimeDist(Date date, int serverId) {
        Date today0Am = DateUtils.dayAm0(date);
        Date lastDayOAm = DateUtils.lastDayAm0(date);

        String sql = "SELECT player_id,login_time,time FROM logout_log WHERE time >= ? AND time < ? AND server_id = ?";
        return jdbcTemplate.queryForList(sql, lastDayOAm.getTime(), today0Am.getTime(), serverId);
    }

    /**
     * 首天等级分布
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> firstDayLevelStatis(int serverId, long startTime, long endTime) {
        String sql = "SELECT player_id,MAX(level) AS level " +
                "FROM logout_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? AND regist_day = 1 " +
                "GROUP BY player_id";

        return jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
    }

}
