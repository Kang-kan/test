package com.xxgame.admin.web.modules.datasummary.dao;

import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 到达创角界面人数统计
 */
@Service
public class ClientAccountLogDao extends AbstractDao {

    /**
     * 到达创角界面人数统计
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> createRoleUiCounter(int serverId, long startTime, long endTime) {
        String sql = "SELECT COUNT(DISTINCT(`account`)) AS count,`channel_id`,`server_id`,`type` FROM client_account_log " +
                "WHERE time >= ? AND time < ? AND server_id = ? " +
                "GROUP BY `channel_id`,`server_id`,`type`";

        return this.jdbcTemplate.queryForList(sql, startTime, endTime, serverId);
    }

}
