package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 道具日志
 */
@Service
public class ItemLogDao extends AbstractDao {

    /**
     * 分页查找玩家道具日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家道具日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家道具日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param itemId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, int itemId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND item_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND item_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, itemId);
    }

    /**
     * 分页查找玩家道具消费日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `count` < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `count` < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家道具消费日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家道具消费日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, long startTime, long endTime, int itemId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` < 0 AND item_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` < 0 AND item_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, itemId);
    }

    /**
     * 分页查找玩家道具产出日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `count` > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `count` > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家道具产出日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, long startTime, long endTime, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime);
    }

    /**
     * 分页查找玩家道具产出日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param itemId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, long startTime, long endTime, int itemId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` > 0 AND item_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM item_log WHERE player_id = ? AND `time` >= ? AND `time` < ? AND `count` > 0 AND item_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId, startTime, endTime, itemId);
    }

}
