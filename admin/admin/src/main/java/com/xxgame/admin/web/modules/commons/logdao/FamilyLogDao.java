package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 仙盟日志
 */
@Service
public class FamilyLogDao extends AbstractDao {

    /**
     * 分页查找玩家仙盟日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM family_log WHERE op_player_id = ? OR target_Player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM family_log WHERE op_player_id = ? OR target_Player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, playerId);
    }

    /**
     * 根据仙盟id分页查找仙盟日志
     * @param familyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findByFamilyId(long familyId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM family_log WHERE family_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM family_log WHERE family_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, familyId);
    }

}
