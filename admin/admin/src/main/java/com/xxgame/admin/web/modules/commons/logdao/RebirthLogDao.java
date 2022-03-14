package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 转生日志
 */
@Service
public class RebirthLogDao extends AbstractDao {

    /**
     * 查找玩家转生日志
     * @param playerId
     * @return
     */
    public List<Map<String, Object>> findPlayerLog(long playerId) {
        String sql = "SELECT * FROM rebirth_log WHERE player_id = ? ORDER BY id DESC";
        return this.jdbcTemplate.queryForList(sql, playerId);
    }

}
