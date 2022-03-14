package com.xxgame.admin.web.modules.onlinestatis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.analysis.service.OnlineStatisAnalyzer;
import com.xxgame.admin.web.modules.onlinestatis.controller.model.DailyRealOnlineStatisDto;
import com.xxgame.admin.web.modules.onlinestatis.controller.model.OnlineTimeDistDto;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.OnlineStatisDist;
import com.xxgame.admin.web.modules.onlinestatis.service.OnlineStatisService;
import com.xxgame.admin.web.util.DateUtils;
import com.xxgame.admin.web.util.HttpUtils;
import com.xxgame.admin.web.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("onlinestatis")
@Api(tags = "在线统计")
public class OnlinestatisController extends BaseController {

    /**
     * 内部ip
     */
    @Value("${social.innerips}")
    private String innerIps;

    @Autowired
    private OnlineStatisService onlineStatisService;
    @Autowired
    private OnlinestatisDtoConverter onlinestatisDtoConverter;
    @Autowired
    private OnlineStatisAnalyzer onlineStatisAnalyzer;

    @RequiresPermissions("realTimeOnline:query")
    @GetMapping(value = "realTimeOnlines")
    @ApiOperation(value = "实时在线-多选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "int"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "int"),
            @ApiImplicitParam(name = "serverIds", value = "字符串数组")
    })
    public Result<PageDto<DailyRealOnlineStatisDto>> realTimeOnlines(@RequestParam int startTime, @RequestParam int endTime,
                                                                     @RequestParam String serverIds,
                                                                     @RequestParam int pageNo, @RequestParam int pageSize) {

        if (StringUtils.isAnyBlank(serverIds)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }

        List<Integer> serverIdList = JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){});
        this.checkServerRight(serverIdList);

        Page<DailyOnlineStatis> pageResult = onlineStatisService.findOnlineStatis(startTime, endTime, serverIdList, pageNo, pageSize);
        List<DailyRealOnlineStatisDto> dtos = onlinestatisDtoConverter.toDailyRealOnlineStatisDtos(pageResult.getContent());

        PageDto<DailyRealOnlineStatisDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("onlineTimeDist:query")
    @GetMapping(value = "onlineTimeDist")
    @ApiOperation(value = "在线时长分布")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id，可为空", type = "integer")
    })
    public Result<PageDto<OnlineTimeDistDto>> onlineTimeDist(@RequestParam(required = false) Integer serverId,
                                                             @RequestParam int startTime, @RequestParam int endTime,
                                                             @RequestParam int pageNo, @RequestParam int pageSize) {

        // 查询所有的
        Page<OnlineStatisDist> pageResult = null;
        if (serverId == null) {
            pageResult = onlineStatisService.findOnlineDist(startTime, endTime, pageNo, pageSize);
        } else {
            pageResult = onlineStatisService.findOnlineDist(startTime, endTime, serverId, pageNo, pageSize);
        }

        List<OnlineTimeDistDto> dtos = onlinestatisDtoConverter.toOnlineTimeDistDtos(pageResult.getContent());

        PageDto<OnlineTimeDistDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @GetMapping(value = "repairData")
    @ApiOperation(value = "修复数据-web端不用接")
    public Result<String> repairData(@RequestParam long dataHour) {
        String requestIp = HttpUtils.getRequestIp(this.servletRequest);
        Pattern[] allowIpPatterns = IpUtils.ipPatterns(innerIps);
        boolean innerIp = IpUtils.isAllowIp(allowIpPatterns, requestIp);
        if (!innerIp) {
            return Result.error(ResultCode.NO_RIGHT, "非法的ip请求");
        }

        logger.warn("开始修复每日在线统计数据，日期时间:{}", dataHour);
        Date date = DateUtils.numberToDate(dataHour, DateUtils.PATTERN_YYYYMMDDHH);
        onlineStatisAnalyzer.analyzeOnlineStatis(date);

        return Result.success("OK");
    }

}
