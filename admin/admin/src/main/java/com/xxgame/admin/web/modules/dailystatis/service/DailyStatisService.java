package com.xxgame.admin.web.modules.dailystatis.service;

import com.xxgame.admin.web.modules.dailystatis.controller.model.LoginLossStatisDto;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyChargeStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyLoginLossStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyRegistStatis;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 日常数据统计
 */
public interface DailyStatisService {

    /**
     * 充值统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyChargeStatis> findChargeStatis(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param serverId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int serverId, int pageNo, int pageSize);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, String channelId, int pageNo, int pageSize);

    /**
     * 注册统计
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyRegistStatis> findRegistStatis(int startTime, int endTime, int pageNo, int pageSize);

    /**
     * 登录留存统计
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailyLoginLossStatis> findLossStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize);

    /**
     * 登录留存
     * @param startTime
     * @param endTime
     * @return
     */
    List<LoginLossStatisDto> loginRetain(long startTime, long endTime);

}
