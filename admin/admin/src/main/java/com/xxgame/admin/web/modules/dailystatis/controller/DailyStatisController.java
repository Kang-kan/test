package com.xxgame.admin.web.modules.dailystatis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.dailystatis.controller.model.*;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyChargeStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyLoginLossStatis;
import com.xxgame.admin.web.modules.dailystatis.entity.DailyRegistStatis;
import com.xxgame.admin.web.modules.dailystatis.service.DailyStatisService;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.service.OnlineStatisService;
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

import java.util.List;

@RestController
@RequestMapping("dailystatis")
@Api(tags = "日常数据统计")
public class DailyStatisController extends BaseController {

    @Autowired
    private DailyStatisService dailyStatisService;
    @Autowired
    private DailyStatisDtoConverter dailyStatisDtoConverter;
    @Autowired
    private OnlineStatisService onlineStatisService;

    @RequiresPermissions("loginRetain:query")
    @GetMapping(value = "loginRetains")
    @ApiOperation(value = "登录留存-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<LoginLossStatisV1Dto>> loginRetains(@RequestParam String channelIds, @RequestParam String serverIds,
                                                              @RequestParam int startTime, @RequestParam int endTime,
                                                              @RequestParam int pageNo, @RequestParam int pageSize) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        Page<DailyLoginLossStatis> pageResult = dailyStatisService.findLossStatis(startTime, endTime, serverIdList, channelIdList, pageNo, pageSize);
        List<LoginLossStatisV1Dto> dtos = dailyStatisDtoConverter.toLoginLossStatisV1Dtos(pageResult.getContent());

        PageDto<LoginLossStatisV1Dto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("registStatis:query")
    @GetMapping(value = "registStatis")
    @ApiOperation(value = "注册统计")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "channelId", value = "渠道，可为空", type = "string"),
            @ApiImplicitParam(name = "serverId", value = "服务器id，可为空", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<PageDto<DailyRegistStatisDto>> registStatis(@RequestParam(required = false) String channelId,
                                                              @RequestParam(required = false) Integer serverId,
                                                              @RequestParam int startTime, @RequestParam int endTime,
                                                              @RequestParam int pageNo, @RequestParam int pageSize) {

        // 查询所有的
        Page<DailyRegistStatis> pageResult = null;
        if (channelId == null && serverId == null) {
            pageResult = dailyStatisService.findRegistStatis(startTime, endTime, pageNo, pageSize);
        } else if (channelId != null) {
            pageResult = dailyStatisService.findRegistStatis(startTime, endTime, channelId, pageNo, pageSize);
        } else if (serverId != null) {
            pageResult = dailyStatisService.findRegistStatis(startTime, endTime, serverId, pageNo, pageSize);
        } else {
            pageResult = dailyStatisService.findRegistStatis(startTime, endTime, serverId, channelId, pageNo, pageSize);
        }
        List<DailyRegistStatisDto> dtos = dailyStatisDtoConverter.toDailyRegistStatisDtos(pageResult.getContent());

        PageDto<DailyRegistStatisDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("registStatis:query")
    @GetMapping(value = "registStatiss")
    @ApiOperation(value = "注册统计-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<DailyRegistStatisDto>> registStatiss(@RequestParam String channelIds, @RequestParam String serverIds,
                                                               @RequestParam int startTime, @RequestParam int endTime,
                                                               @RequestParam int pageNo, @RequestParam int pageSize) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        Page<DailyRegistStatis> pageResult = dailyStatisService.findRegistStatis(startTime, endTime, serverIdList, channelIdList, pageNo, pageSize);
        List<DailyRegistStatisDto> dtos = dailyStatisDtoConverter.toDailyRegistStatisDtos(pageResult.getContent());

        PageDto<DailyRegistStatisDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("onlineStatis:query")
    @GetMapping(value = "onlineStatiss")
    @ApiOperation(value = "在线统计-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "channelIds", value = "字符串数组"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<DailyOnlineStatisDto>> onlineStatiss(@RequestParam String channelIds, @RequestParam String serverIds,
                                                               @RequestParam int startTime, @RequestParam int endTime,
                                                               @RequestParam int pageNo, @RequestParam int pageSize) {

        if (StringUtils.isAnyBlank(channelIds, serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<String> channelIdList = JSON.parseObject(channelIds, new TypeReference<List<String>>(){});
        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});

        this.checkServerRight(serverIdList);

        Page<DailyChannelOnlineStatis> pageResult = onlineStatisService.findChannelOnlineStatis(startTime, endTime, serverIdList, channelIdList, pageNo, pageSize);
        List<DailyOnlineStatisDto> dtos = dailyStatisDtoConverter.toDailyOnlineStatisDtos(pageResult.getContent());

        PageDto<DailyOnlineStatisDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("chargeStatis:query")
    @GetMapping(value = "chargeStatis")
    @ApiOperation(value = "充值统计")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "int")
    })
    @ServerRight(argsIndex = 1)
    public Result<PageDto<DailyChargeStatisDto>> chargeStatis(@RequestParam String channelId, @RequestParam int serverId,
                                                              @RequestParam int startTime, @RequestParam int endTime,
                                                              @RequestParam int pageNo, @RequestParam int pageSize) {

        Page<DailyChargeStatis> pageResult = dailyStatisService.findChargeStatis(startTime, endTime,serverId, channelId, pageNo, pageSize);
        List<DailyChargeStatisDto> dtos = dailyStatisDtoConverter.toDailyChargeStatisDtos(pageResult.getContent());

        PageDto<DailyChargeStatisDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

}
