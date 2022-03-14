package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * boss击杀日志
 */
@Service
public class BossDeadLogDao extends AbstractDao {

    /**
     * 分页查找玩家boss击杀日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM boss_deal_log WHERE owner_id = ? AND owner_type = 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM boss_deal_log WHERE owner_id = ? AND owner_type = 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家boss击杀日志
     * @param playerId
     * @param playType
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int playType, int pageNo, int pageSize) {
        String sql = "SELECT * FROM boss_deal_log WHERE owner_id = ? AND owner_type = 0 AND play_Type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM boss_deal_log WHERE owner_id = ? AND owner_type = 0 AND play_Type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, playType);
    }

    /**
     * 根据战斗id查找
     * @param battleId
     * @return
     */
    public List<Map<String, Object>> findByBattleId(long battleId) {
        String sql = "SELECT * FROM boss_deal_log WHERE battle_id = ? ORDER BY id DESC";
        return this.jdbcTemplate.queryForList(sql, battleId);
    }

    /**
     * 根据服务器id玩法类型时间段分页查找
     * @param battleServer
     * @param playType
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findByTimeAndSrv(int battleServer, int playType,
                                                         long startTime, long endTime,
                                                         int pageNo, int pageSize) {
        String sql = "SELECT * FROM boss_deal_log WHERE time >= ? AND time < ? AND battle_server = ? AND play_type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM boss_deal_log WHERE time >= ? AND time < ? AND battle_server = ? AND play_type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, startTime, endTime, battleServer, playType);
    }

}
