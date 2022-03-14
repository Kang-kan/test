package com.xxgame.admin.web.modules.commons.logdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 礼包码日志
 */
@Service
public class SerialGiftLogDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * 查找玩家礼包码日志
     * @param playerId
     * @return
     */
    public List<Map<String, Object>> findPlayerLog(long playerId) {
        String sql = "SELECT * FROM serial_gift_log WHERE player_id = ? ORDER BY id DESC";
        return jdbcTemplate.queryForList(sql, playerId);
    }

    /**
     * 根据礼包码查找日志
     * @param cdKey
     * @return
     */
    public List<Map<String, Object>> findByCdKey(String cdKey) {
        String sql = "SELECT * FROM serial_gift_log WHERE cd_key = ? ORDER BY id DESC";
        return jdbcTemplate.queryForList(sql, cdKey);
    }

}
