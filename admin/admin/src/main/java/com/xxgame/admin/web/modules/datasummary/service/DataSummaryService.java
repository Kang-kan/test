package com.xxgame.admin.web.modules.datasummary.service;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.datasummary.controller.model.*;
import com.xxgame.admin.web.modules.datasummary.entity.DailySummary;
import com.xxgame.admin.web.modules.datasummary.entity.MonthlyActive;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DataSummaryService
 */
public interface DataSummaryService {

    /**
     * 分页查找月活跃汇总
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<MonthlyActive> findMonthlyActive(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize);

    /**
     * 分页查找月活跃汇总
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<MonthlyActive> findMonthlyActive(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize);

    /**
     * 分页查找各服充值数据
     * @param startTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageDto<SrvListChargeDto> findServerCharge(int startTime, int pageNo, int pageSize);

    /**
     * 分页查找各服时间段充值
     * @param startTime
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageDto<SrvListChargeDto> findChannelServerCharge(int startTime, String channelId, int pageNo, int pageSize);

    /**
     * 查找各服时间段充值
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @return
     */
    List<SrvChargeDto> findChannelServerCharge(int startTime, int endTime, int serverId, String channelId);

    /**
     * 分页查找单服数据简表
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageDto<SingleSrvSimpleDto> singleSrvSimple(int startTime, int endTime, int pageNo, int pageSize);

    /**
     * 分页查找单服数据简表
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageDto<SingleSrvSimpleDto> singleSrvSimple(int startTime, int endTime, List<Integer> serverIds, int pageNo, int pageSize);

    /**
     * 当日汇总
     * @param channelId
     * @param serverId
     * @return
     */
    TodaySummaryDto todaySummary(String channelId, int serverId);

    /**
     * 小时汇总
     * @param channelId
     * @param serverId
     * @param dateTime
     * @return
     */
    List<HourSummaryDto> hourSummarys(String channelId, int serverId, int dateTime);

    /**
     * 每日汇总
     * @param startTime
     * @param endTime
     * @param serverId
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailySummary> findDailySummary(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize);

    /**
     * 每日汇总
     * @param startTime
     * @param endTime
     * @param serverIds
     * @param channelIds
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<DailySummary> findDailySummary(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize);
}
