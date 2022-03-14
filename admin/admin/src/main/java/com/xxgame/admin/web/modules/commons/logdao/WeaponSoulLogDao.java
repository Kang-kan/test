package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 器灵日志
 */
@Service
public class WeaponSoulLogDao extends AbstractDao {

    /**
     * 分页查找玩家器灵日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM weapon_soul_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM weapon_soul_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

}
