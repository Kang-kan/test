package com.xxgame.admin.web.modules.chargestatis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.chargestatis.entity.ChargeLossStatis;
import com.xxgame.admin.web.modules.commons.logdao.LoginLogDao;
import com.xxgame.admin.web.modules.chargestatis.controller.model.*;
import com.xxgame.admin.web.modules.commons.logdao.ChargeStatisDao;
import com.xxgame.admin.web.modules.chargestatis.service.ChargeStatisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("chargestatis")
@Api(tags = "充值统计")
public class ChargeStatisController extends BaseController {

    @Autowired
    private ChargeStatisDao chargeStatisDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private ChargeStatisService chargeStatisService;
    @Autowired
    private ChargeStatisDtoConverter chargeStatisDtoConverter;

    @RequiresPermissions("chargeMonthSummary:query")
    @GetMapping(value = "chargeMonthSummary")
    @ApiOperation(value = "月充值汇总")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<ChargeMonthDto> chargeMonthSummary(@RequestParam String channelId, @RequestParam int serverId,
                                                     @RequestParam int startTime, @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        Map<String, Object> totalStatis = chargeStatisDao.chargeTotalStatis(channelId, serverId, startDate.getTime(), endDate.getTime());
        int loginCount = loginLogDao.loginCount(serverId, channelId, startDate.getTime(), endDate.getTime());

        ChargeMonthDto dto = chargeStatisDtoConverter.toChargeMonthDto(totalStatis, channelId, loginCount);
        return Result.success(dto);
    }

    @RequiresPermissions("chargeMonthSummary:query")
    @GetMapping(value = "chargeMonthSummarys")
    @ApiOperation(value = "月充值汇总-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<List<ChargeMonthDto>> chargeMonthSummarys(@RequestParam String channelIds, @RequestParam String serverIds,
                                                            @RequestParam int startTime, @RequestParam int endTime) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<ChargeMonthDto> list = new LinkedList<>();
        for (String channelId : channelIdList) {
            for (int serverId : serverIdList) {
                Map<String, Object> totalStatis = chargeStatisDao.chargeTotalStatis(channelId, serverId, startDate.getTime(), endDate.getTime());
                int loginCount = loginLogDao.loginCount(serverId, channelId, startDate.getTime(), endDate.getTime());
                ChargeMonthDto dto = chargeStatisDtoConverter.toChargeMonthDto(totalStatis, channelId, loginCount);
                dto.setServerId(serverId);

                list.add(dto);
            }
        }

        return Result.success(list);
    }

    @RequiresPermissions("chargeSingleDist:query")
    @GetMapping(value = "chargeSingleDistV1")
    @ApiOperation(value = "单笔充值分布V1")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<List<SingleChargeDistDto>> chargeSingleDistV1(@RequestParam String channelId, @RequestParam int serverId,
                                                                @RequestParam int startTime, @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> rowMaps = chargeStatisDao.singleChargeDist(channelId, serverId, startDate.getTime(), endDate.getTime());
        List<SingleChargeDistDto> dtos = chargeStatisDtoConverter.toSingleChargeDistDtosV1(rowMaps);
        return Result.success(dtos);
    }

    @RequiresPermissions("chargeSingleDist:query")
    @GetMapping(value = "chargeSingleDistsV1")
    @ApiOperation(value = "单笔充值分布V1-全渠道")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 0)
    public Result<List<SingleChargeDistDto>> chargeSingleDistsV1(@RequestParam int serverId, @RequestParam int startTime, @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> rowMaps = chargeStatisDao.singleChargeDist(serverId, startDate.getTime(), endDate.getTime());
        List<SingleChargeDistDto> dtos = chargeStatisDtoConverter.toALLChannelSingleChargeDistDtosV1(rowMaps);
        return Result.success(dtos);
    }

    @RequiresPermissions("chargeTotalDist:query")
    @GetMapping(value = "chargeTotalDist")
    @ApiOperation(value = "累计充值分布")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<List<TotalChargeDistDto>> chargeTotalDist(@RequestParam String channelId, @RequestParam int serverId,
                                                            @RequestParam int startTime, @RequestParam int endTime) {
        if (serverId <= 0) {
            return Result.error(ResultCode.PARAM_EMPTY, "请选择服务器");
        }
        if (StringUtils.isBlank(channelId)) {
            return Result.error(ResultCode.PARAM_EMPTY, "请选择渠道");
        }

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> resultMap = chargeStatisDao.totalChargeDist(channelId, serverId, startDate.getTime(), endDate.getTime());
        List<TotalChargeDistDto> dtos = chargeStatisDtoConverter.toTotalChargeDistDtos(resultMap);

        return Result.success(dtos);
    }

    @RequiresPermissions("chargeFirstLevelDist:query")
    @GetMapping(value = "chargeFirstLevelDist")
    @ApiOperation(value = "首充等级分布")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<List<FirstChargeLevelDistDto>> chargeFirstLevelDist(@RequestParam String channelId, @RequestParam int serverId,
                                                                      @RequestParam int startTime, @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> resultMap = chargeStatisDao.firstChargeLevelDist(channelId, serverId, startDate.getTime(), endDate.getTime());
        List<FirstChargeLevelDistDto> dtos = chargeStatisDtoConverter.toFirstChargeLevelDistDtos(resultMap);

        return Result.success(dtos);
    }

    @RequiresPermissions("chargeLevelDist:query")
    @GetMapping(value = "chargeLevelDist")
    @ApiOperation(value = "付费等级分布")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<List<ChargeLevelDistDto>> chargeLevelDist(@RequestParam String channelId, @RequestParam int serverId,
                                                            @RequestParam int startTime, @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> resultMap = chargeStatisDao.chargeLevelDist(channelId, serverId, startDate.getTime(), endDate.getTime());
        List<ChargeLevelDistDto> dtos = chargeStatisDtoConverter.toChargeLevelDistDtos(resultMap);

        return Result.success(dtos);
    }

    @RequiresPermissions("chargeLoginLoss:query")
    @GetMapping(value = "chargeLoginLoss")
    @ApiOperation(value = "付费登录流失")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<ChargeLossDto> chargeLoginLoss(@RequestParam String channelId, @RequestParam int serverId,
                                                 @RequestParam int startTime, @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        Map<String, Object> totalStatis = chargeStatisDao.chargeTotalStatis(channelId, serverId, startDate.getTime(), endDate.getTime());
        int lossCount = chargeStatisService.queryLossCount(serverId, channelId, startDate, endDate);
        ChargeLossDto dto = chargeStatisDtoConverter.toChargeLossDto(channelId, totalStatis, lossCount);

        return Result.success(dto);
    }

    @RequiresPermissions("chargeLoginLoss:query")
    @GetMapping(value = "chargeLoginLosss")
    @ApiOperation(value = "付费登录流失-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<ChargeLossDto>> chargeLoginLosss(@RequestParam String channelIds, @RequestParam String serverIds,
                                                  @RequestParam int startTime, @RequestParam int endTime,
                                                  @RequestParam int pageNo, @RequestParam int pageSize) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        Page<ChargeLossStatis> pages = chargeStatisService.findLossStatis(startTime, endTime, serverIdList, channelIdList, pageNo, pageSize);
        List<ChargeLossDto> dtos = chargeStatisDtoConverter.toChargeLossDtos(pages.getContent());
        PageDto<ChargeLossDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pages);

        return Result.success(pageDto);
    }

    @RequiresPermissions("costGoldRank:query")
    @GetMapping(value = "costGoldRank")
    @ApiOperation(value = "元宝消耗排行")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<List<CostGoldRankDto>> costGoldRank(@RequestParam String channelId, @RequestParam int serverId,
                                                      @RequestParam int startTime, @RequestParam int endTime) {

        Date startDate = this.dayToDate(startTime);
        Date endDate = this.dayToDate(endTime + 1);

        List<Map<String, Object>> rankMaps = chargeStatisDao.costRank(channelId, serverId, startDate.getTime(), endDate.getTime());
        List<CostGoldRankDto> dtos = chargeStatisDtoConverter.toCostGoldRankDtos(rankMaps);

        return Result.success(dtos);
    }

}
