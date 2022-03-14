package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 内币日志
 */
@Service
public class GmGoldLogDao extends AbstractDao {

    /**
     * 分页查找玩家内币日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gm_gold_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gm_gold_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家内币消费日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gm_gold_log WHERE player_id = ? AND change_num < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gm_gold_log WHERE player_id = ? AND change_num < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家内币产出日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM gm_gold_log WHERE player_id = ? AND change_num > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM gm_gold_log WHERE player_id = ? AND change_num > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

}
