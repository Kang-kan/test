package com.xxgame.admin.web.modules.commons.logdao;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.commons.AbstractDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 其它货币资源
 */
@Service
public class OtherResourceLogDao extends AbstractDao {

    /**
     * 分页查找玩家其它货币日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM other_resource_log WHERE player_id = ? ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM other_resource_log WHERE player_id = ?";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家其它货币日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param currencys
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerLog(long playerId, long startTime, long endTime, List<Integer> currencys, int pageNo, int pageSize) {
        String sql = "SELECT * FROM other_resource_log WHERE player_id = :playerId AND time >= :startTime AND time < :endTime AND `type` IN(:currencys) ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM other_resource_log WHERE player_id = :playerId AND time >= :startTime AND time < :endTime AND `type` IN(:currencys)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("playerId", playerId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("currencys", currencys);

        return this.queryPage(sql, countSql, pageNo, pageSize, paramMap);
    }

    /**
     * 分页查找玩家其它货币消费日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM other_resource_log WHERE player_id = ? AND change_num < 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM other_resource_log WHERE player_id = ? AND change_num < 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家其它货币消费日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param currencys
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerDecLog(long playerId, long startTime, long endTime, List<Integer> currencys, int pageNo, int pageSize) {
        String sql = "SELECT * FROM other_resource_log WHERE player_id = :playerId AND time >= :startTime AND time < :endTime AND change_num < 0 AND type IN(:currencys) ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM other_resource_log WHERE player_id = :playerId AND time >= :startTime AND time < :endTime AND change_num < 0 AND type IN(:currencys)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("playerId", playerId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("currencys", currencys);

        return this.queryPage(sql, countSql, pageNo, pageSize, paramMap);
    }

    /**
     * 分页查找玩家其它货币产出日志
     * @param playerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, int pageNo, int pageSize) {
        String sql = "SELECT * FROM other_resource_log WHERE player_id = ? AND change_num > 0 ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM other_resource_log WHERE player_id = ? AND change_num > 0";
        return this.queryPage(sql, countSql, pageNo, pageSize, playerId);
    }

    /**
     * 分页查找玩家其它货币产出日志
     * @param playerId
     * @param startTime
     * @param endTime
     * @param currencys
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageDto<Map<String, Object>> findPlayerRewardLog(long playerId, long startTime, long endTime, List<Integer> currencys, int pageNo, int pageSize) {
        String sql = "SELECT * FROM other_resource_log WHERE player_id = :playerId AND time >= :startTime AND time < :endTime AND change_num > 0 AND type IN(:currencys) ORDER BY id DESC";
        String countSql = "SELECT COUNT(*) FROM other_resource_log WHERE player_id = :playerId AND time >= :startTime AND time < :endTime AND change_num > 0 AND type IN(:currencys)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("playerId", playerId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("currencys", currencys);

        return this.queryPage(sql, countSql, pageNo, pageSize, paramMap);
    }

}
