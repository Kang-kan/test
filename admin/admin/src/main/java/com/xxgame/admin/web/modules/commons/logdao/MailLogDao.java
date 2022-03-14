package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 邮件日志
 */
@Service
public class MailLogDao extends AbstractDao {

    /**
     * 分页查找玩家邮件日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM mail_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM mail_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家邮件日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM mail_log WHERE player_id = ? AND time >= ? AND time < ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM mail_log WHERE player_id = ? AND time >= ? AND time < ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家邮件日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param title
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, String title, int pageNo, int pageSize) {
        title = "%" + title + "%";

        String sql = "SELECT * FROM mail_log WHERE player_id = ? AND time >= ? AND time < ? AND title like ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM mail_log WHERE player_id = ? AND time >= ? AND time < ? AND title like ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, title);
    }

    /**
     * 根据服务器id邮件标题分页查找
     * @param serverId
     * @param title
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findBySrvTitle(int serverId, String title, int pageNo, int pageSize) {
        title = "%" + title + "%";
        String sql = "SELECT * FROM mail_log WHERE server_id = ? AND title like ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM mail_log WHERE server_id = ? AND title like ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, serverId, title);
    }

}
