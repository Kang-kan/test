package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 玩家统计信息
 */
@Service
public class PlayerStatisLogDao extends AbstractDao {

    /**
     * 分页查找玩家统计信息
     * @param serverId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findLeveldist(int serverId, int startTime, int endTime, int pageNo, int pageSize) {
        Object[] args = new Object[]{ startTime, endTime, serverId };

        String sql = "SELECT * FROM player_statis_log WHERE daily >= ? AND daily <= ? AND server_id = ?";
        String countSql = "SELECT COUNT(*) FROM player_statis_log WHERE daily >= ? AND daily <= ? AND server_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, args);
    }

    /**
     * 根据id查询玩家等级分布
     * @param id
     * @return
     */
    public Map<String, Object> findById(String id) {
        String sql = "SELECT * FROM player_statis_log WHERE id = ?";
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
