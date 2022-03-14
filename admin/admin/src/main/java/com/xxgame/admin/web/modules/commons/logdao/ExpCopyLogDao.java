package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 经验副本领取日志
 */
@Service
public class ExpCopyLogDao extends AbstractDao {

    /**
     * 经验副本统计
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> expCopyStatis(int serverId, long startTime, long endTime) {
        String sql = "SELECT server_id, channel_id, multiple, COUNT(player_id) AS times, COUNT(distinct player_id) AS count " +
                "FROM exp_copy_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? " +
                "GROUP BY server_id, channel_id, multiple";

        return this.jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
    }

}
