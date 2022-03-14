package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 游戏币日志
 */
@Service
public class MoneyLogDao extends AbstractDao {

    /**
     * 分页查找玩家游戏币日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家游戏币日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND time >= ? AND time < ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND time >= ? AND time < ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家游戏币日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param fuctionType
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, String fuctionType, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND function_type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND function_type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, fuctionType);
    }

    /**
     * 分页查找玩家游戏币消费日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND change_num < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND change_num < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家游戏币消费日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家游戏币消费日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param fuctionType
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, long startTime, long endTime, String fuctionType, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0 AND function_type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num < 0 AND function_type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, fuctionType);
    }

    /**
     * 分页查找玩家游戏币产出日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND change_num > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND change_num > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家游戏币产出日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家游戏币产出日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param fuctionType
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, long startTime, long endTime, String fuctionType, int pageNo, int pageSize) {
        String sql = "SELECT * FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0 AND function_type = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM money_log WHERE player_id = ? AND time >= ? AND time < ? AND change_num > 0 AND function_type = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, fuctionType);
    }

}
