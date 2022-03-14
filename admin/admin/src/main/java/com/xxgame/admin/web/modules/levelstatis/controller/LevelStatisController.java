package com.xxgame.admin.web.modules.levelstatis.controller;

import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.levelstatis.controller.model.LevelDistStatisDto;
import com.xxgame.admin.web.modules.levelstatis.controller.model.LevelLossStatisDto;
import com.xxgame.admin.web.modules.commons.logdao.PlayerStatisLogDao;
import com.xxgame.admin.web.modules.levelstatis.controller.model.LevelLossStatisV1Dto;
import com.xxgame.admin.web.modules.levelstatis.entity.DailyLevelLossStatis;
import com.xxgame.admin.web.modules.levelstatis.repository.DailyLevelLossStatisRepository;
import com.xxgame.admin.web.modules.levelstatis.service.LevelStatisService;
import com.xxgame.admin.web.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("levelstatis")
@Api(tags = "等级统计")
public class LevelStatisController extends BaseController {

    @Autowired
    private LevelStatisService levelStatisService;
    @Autowired
    private PlayerStatisLogDao playerStatisLogDao;
    @Autowired
    private DailyLevelLossStatisRepository dailyLevelLossStatisRepository;
    @Autowired
    private LevelStatisDtoConverter levelStatisDtoConverter;

    @RequiresPermissions("leveldist:query")
    @GetMapping(value = "leveldist")
    @ApiOperation(value = "等级分布")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 0)
    public Result<PageDto<LevelDistStatisDto>> leveldist(@RequestParam int serverId, @RequestParam int startTime,
                                                         @RequestParam int endTime, @RequestParam int pageNo,
                                                         @RequestParam int pageSize) {

        PageDto<Map<String, Object>> pageRowMap = playerStatisLogDao.findLeveldist(serverId, startTime, endTime, pageNo, pageSize);
        List<LevelDistStatisDto> dtos = levelStatisDtoConverter.toLevelDistStatisDtos(pageRowMap.getContents());

        PageDto<LevelDistStatisDto> result = new PageDto();
        result.setContents(dtos);
        result.setPageSize(pageRowMap.getPageSize());
        result.setPageNo(pageRowMap.getPageNo());
        result.setTotalPage(pageRowMap.getTotalPage());
        result.setTotalCount(pageRowMap.getTotalCount());

        return Result.success(result);
    }

    @RequiresPermissions("levelloss:query")
    @GetMapping(value = "levelloss")
    @ApiOperation(value = "等级流失")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 0)
    public Result<List<LevelLossStatisDto>> levelloss(@RequestParam int serverId, @RequestParam int startTime, @RequestParam int endTime) {
        Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
        Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);

        String countStatis = levelStatisService.queryLevelCountStatis(serverId);
        String lossStatis = levelStatisService.queryLevelLossStatis(serverId, startDate, endDate);

        Map<Integer, Integer> countMap = levelStatisDtoConverter.toMap(countStatis);
        Map<Integer, Integer> lossMap = levelStatisDtoConverter.toMap(lossStatis);

        List<LevelLossStatisDto> dtos = new ArrayList<>(countMap.size());
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            LevelLossStatisDto dto = new LevelLossStatisDto();
            dto.setLevel(entry.getKey());
            dto.setCount(entry.getValue());
            dto.setLossCount(lossMap.getOrDefault(entry.getKey(), 0));

            dtos.add(dto);
        }

        return Result.success(dtos);
    }

    @RequiresPermissions("levelloss:query")
    @GetMapping(value = "levelloss1")
    @ApiOperation(value = "等级流失1")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "serverId", value = "服务器id", type = "integer")
    })
    @ServerRight(argsIndex = 1)
    public Result<List<LevelLossStatisV1Dto>> levelloss1(@RequestParam int daily, @RequestParam int serverId) {
        List<DailyLevelLossStatis> list = dailyLevelLossStatisRepository.findDailyLoss(daily, serverId);
        List<LevelLossStatisV1Dto> dtos = levelStatisDtoConverter.toLevelLossStatisV1Dtos(list);

        return Result.success(dtos);
    }


}
