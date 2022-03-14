package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 异闻录日志
 */
@Service
public class PokedexLogDao extends AbstractDao {

    /**
     * 分页查找玩家异闻录日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM pokedex_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM pokedex_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家异闻录日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM pokedex_log WHERE player_id = ? AND time >= ? AND time < ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM pokedex_log WHERE player_id = ? AND time >= ? AND time < ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

}
