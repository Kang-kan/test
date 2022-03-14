package com.xxgame.admin.web.modules.datasummary.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.logdao.ExpCopyLogDao;
import com.xxgame.admin.web.modules.datasummary.controller.model.*;
import com.xxgame.admin.web.modules.datasummary.dao.ClientAccountLogDao;
import com.xxgame.admin.web.modules.datasummary.entity.DailySummary;
import com.xxgame.admin.web.modules.datasummary.entity.MonthlyActive;
import com.xxgame.admin.web.modules.datasummary.service.DataSummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("dataSummary")
@Api(tags = "数据汇总-300")
public class DataSummaryController extends BaseController {

    @Autowired
    private DataSummaryService dataSummaryService;
    @Autowired
    private ClientAccountLogDao clientAccountLogDao;
    @Autowired
    private ExpCopyLogDao expCopyLogDao;
    @Autowired
    private DataSummaryDtoConverter dataSummaryDtoConverter;

    @RequiresPermissions("todySummary:query")
    @GetMapping(value = "todaySummary")
    @ApiOperation(value = "当日汇总")
    @ServerRight(argsIndex = 1)
    public Result<TodaySummaryDto> todaySummary(@RequestParam String channelId, @RequestParam int serverId) {
        TodaySummaryDto dto = dataSummaryService.todaySummary(channelId, serverId);
        return Result.success(dto);
    }

    @RequiresPermissions("todySummary:query")
    @GetMapping(value = "todaySummarys")
    @ApiOperation(value = "当日汇总-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<List<TodaySummaryDto>> todaySummarys(@RequestParam String channelIds, @RequestParam String serverIds) {
        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        List<TodaySummaryDto> list = new LinkedList<>();
        for (String channelId : channelIdList) {
            for (int serverId : serverIdList) {
                TodaySummaryDto dto = dataSummaryService.todaySummary(channelId, serverId);
                list.add(dto);
            }
        }

        return Result.success(list);
    }

    @RequiresPermissions("hourSummary:query")
    @GetMapping(value = "hourSummary")
    @ApiOperation(value = "小时汇总")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "dateTime", value = "年月日，如：20190923")
    })
    @ServerRight(argsIndex = 1)
    public Result<List<HourSummaryDto>> hourSummary(@RequestParam String channelId,
                                                    @RequestParam int serverId,
                                                    @RequestParam int dateTime) {

        List<HourSummaryDto> dtos = dataSummaryService.hourSummarys(channelId, serverId, dateTime);
        return Result.success(dtos);
    }

    @RequiresPermissions("hourSummary:query")
    @GetMapping(value = "hourSummarys")
    @ApiOperation(value = "小时汇总-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组"),
            @ApiImplicitParam(name = "dateTime", value = "年月日，如：20190923")
    })
    public Result<List<HourSummaryDto>> hourSummarys(@RequestParam String channelIds,
                                                     @RequestParam String serverIds,
                                                     @RequestParam int dateTime) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        List<HourSummaryDto> list = new LinkedList<>();
        for (String channelId : channelIdList) {
            for (int serverId : serverIdList) {
                List<HourSummaryDto> dtos = dataSummaryService.hourSummarys(channelId, serverId, dateTime);
                list.addAll(dtos);
            }
        }

        return Result.success(list);
    }

    @RequiresPermissions("dailySummary:query")
    @GetMapping(value = "dailySummary")
    @ApiOperation(value = "每日汇总")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20191031", type = "int")
    })
    @ServerRight(argsIndex = 1)
    public Result<PageDto<DailySummaryDto>> dailySummary(@RequestParam String channelId, @RequestParam int serverId,
                                                         @RequestParam int startTime, @RequestParam int endTime,
                                                         @RequestParam int pageNo, @RequestParam int pageSize) {

        Page<DailySummary> pageResult = dataSummaryService.findDailySummary(startTime, endTime, serverId, channelId, pageNo, pageSize);

        List<DailySummaryDto> dtos = dataSummaryDtoConverter.toDailySummaryDtos(pageResult.getContent());
        PageDto<DailySummaryDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("dailySummary:query")
    @GetMapping(value = "dailySummarys")
    @ApiOperation(value = "每日汇总-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20191031", type = "int"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<DailySummaryDto>> dailySummarys(@RequestParam String channelIds, @RequestParam String serverIds,
                                                          @RequestParam int startTime, @RequestParam int endTime,
                                                          @RequestParam int pageNo, @RequestParam int pageSize) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        Page<DailySummary> pageResult = dataSummaryService.findDailySummary(startTime, endTime, serverIdList, channelIdList, pageNo, pageSize);

        List<DailySummaryDto> dtos = dataSummaryDtoConverter.toDailySummaryDtos(pageResult.getContent());
        PageDto<DailySummaryDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("singleSrvSummary:query")
    @GetMapping(value = "singleSrv")
    @ApiOperation(value = "单服数据简表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20191031", type = "int")
    })
    public Result<PageDto<SingleSrvSimpleDto>> singleSrv(@RequestParam int startTime, @RequestParam int endTime,
                                                         @RequestParam int pageNo, @RequestParam int pageSize) {

        if (startTime < 0 || endTime < 0) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        if (endTime < startTime) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        // 查询的范围太大
        if ((endTime - startTime) > 365) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "查询时间范围过大");
        }
        if (pageSize > 50) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        PageDto<SingleSrvSimpleDto> result = dataSummaryService.singleSrvSimple(startTime, endTime, pageNo, pageSize);
        return Result.success(result);
    }

    @RequiresPermissions("singleSrvSummary:query")
    @GetMapping(value = "singleSrvs")
    @ApiOperation(value = "单服数据简表-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20191031", type = "int"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<SingleSrvSimpleDto>> singleSrvs(@RequestParam int startTime, @RequestParam int endTime,
                                                          @RequestParam String serverIds,
                                                          @RequestParam int pageNo, @RequestParam int pageSize) {

        if (startTime < 0 || endTime < 0) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        if (endTime < startTime) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        // 查询的范围太大
        if ((endTime - startTime) > 365) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "查询时间范围过大");
        }
        if (pageSize > 50) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        PageDto<SingleSrvSimpleDto> result = dataSummaryService.singleSrvSimple(startTime, endTime, serverIdList, pageNo, pageSize);
        return Result.success(result);
    }

    @RequiresPermissions("singleSrvChargeSummary:query")
    @GetMapping(value = "serverCharge")
    @ApiOperation(value = "区服充值汇总")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190910")
    })
    public Result<PageDto<SrvListChargeDto>> serverCharge(@RequestParam int startTime, @RequestParam int pageNo, @RequestParam int pageSize) {
        PageDto<SrvListChargeDto> pageDto = dataSummaryService.findServerCharge(startTime, pageNo, pageSize);
        return Result.success(pageDto);
    }

    @RequiresPermissions("monthlyActiveSummary:query")
    @GetMapping(value = "monthlyActive")
    @ApiOperation(value = "月活跃汇总")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月，如：201909", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月，如：201910", type = "int")
    })
    @ServerRight(argsIndex = 0)
    public Result<PageDto<MonthlyActiveDto>> monthlyActive(@RequestParam int serverId, @RequestParam String channelId,
                                                        @RequestParam int startTime, @RequestParam int endTime,
                                                        @RequestParam int pageNo, @RequestParam int pageSize) {

        Page<MonthlyActive> pageResult = dataSummaryService.findMonthlyActive(startTime, endTime, serverId, channelId, pageNo, pageSize);
        List<MonthlyActiveDto> dtos = dataSummaryDtoConverter.toMonthlyActiveDtos(pageResult.getContent());

        PageDto<MonthlyActiveDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("monthlyActiveSummary:query")
    @GetMapping(value = "monthlyActives")
    @ApiOperation(value = "月活跃汇总-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月，如：201909", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月，如：201910", type = "int"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<MonthlyActiveDto>> monthlyActives(@RequestParam String serverIds, @RequestParam String channelIds,
                                                            @RequestParam int startTime, @RequestParam int endTime,
                                                            @RequestParam int pageNo, @RequestParam int pageSize) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        Page<MonthlyActive> pageResult = dataSummaryService.findMonthlyActive(startTime, endTime, serverIdList, channelIdList, pageNo, pageSize);
        List<MonthlyActiveDto> dtos = dataSummaryDtoConverter.toMonthlyActiveDtos(pageResult.getContent());

        PageDto<MonthlyActiveDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("srvsChargeSummary:query")
    @GetMapping(value = "serverListCharges")
    @ApiOperation(value = "各服时间段充值-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20191120"),
            @ApiImplicitParam(name = "endTime", value = "结束年月，如：20191120"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<List<SrvChargeDto>> serverListCharges(@RequestParam int startTime, @RequestParam int endTime,
                                                        @RequestParam String channelIds, @RequestParam String serverIds) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        List<SrvChargeDto> list = new ArrayList<>();
        for (String channelId : channelIdList) {
            for (int serverId : serverIdList) {
                List<SrvChargeDto> dtos = dataSummaryService.findChannelServerCharge(startTime, endTime, serverId, channelId);
                list.addAll(dtos);
            }
        }

        Collections.sort(list, (d1, d2) -> {
            return Integer.valueOf(d2.getDateTime()).compareTo(d1.getDateTime());
        });
        return Result.success(list);
    }

    @RequiresPermissions("createRoleUiCounter:query")
    @GetMapping(value = "createRoleUiCounter")
    @ApiOperation(value = "注册埋点-308")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20191120"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20191121")
    })
    @ServerRight(argsIndex = 0)
    public Result<Collection<CreateRoleUiCountDto>> createRoleUiCounter(@RequestParam int serverId,
                                                                        @RequestParam int startTime,
                                                                        @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> rowMaps = clientAccountLogDao.createRoleUiCounter(serverId, startDate.getTime(), endDate.getTime());
        Collection<CreateRoleUiCountDto> dtos = dataSummaryDtoConverter.toCreateRoleUiCountDtos(rowMaps);

        return Result.success(dtos);
    }

    @RequiresPermissions("expCopyStatis:query")
    @GetMapping(value = "expCopyStatis")
    @ApiOperation(value = "经验副本统计-309")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20191120"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20191121")
    })
    @ServerRight(argsIndex = 0)
    public Result<Collection<ExpCopyDto>> expCopyStatis(@RequestParam int serverId,
                                                        @RequestParam int startTime,
                                                        @RequestParam int endTime) {
        if (serverId <= 0) {
            return Result.error(ResultCode.PARAM_ERROR, "请选择服务器");
        }

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> rowMaps = expCopyLogDao.expCopyStatis(serverId, startDate.getTime(), endDate.getTime());
        Collection<ExpCopyDto> dtos = dataSummaryDtoConverter.toExpCopyDtos(rowMaps);

        return Result.success(dtos);
    }

}
