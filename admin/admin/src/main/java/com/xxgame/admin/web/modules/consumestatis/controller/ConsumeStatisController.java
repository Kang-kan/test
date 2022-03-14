package com.xxgame.admin.web.modules.consumestatis.controller;

import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.consumestatis.controller.model.GoldConsumeStatisDto;
import com.xxgame.admin.web.modules.consumestatis.controller.model.MarketConsumeStatisDto;
import com.xxgame.admin.web.modules.consumestatis.entity.GoldConsumeStatis;
import com.xxgame.admin.web.modules.consumestatis.entity.MarketStatis;
import com.xxgame.admin.web.modules.consumestatis.service.ConsumeStatisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consumestatis")
@Api(tags = "消耗统计")
public class ConsumeStatisController extends BaseController {

    @Autowired
    private ConsumeStatisService consumeStatisService;
    @Autowired
    private ConsumeStatisDtoConverter consumeStatisDtoConverter;

    @RequiresPermissions("goldFunctionStatis:query")
    @GetMapping(value = "goldFunctionStatis")
    @ApiOperation(value = "元宝消耗统计")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id，可为空", type = "integer")
    })
    @ServerRight(argsIndex = 0)
    public Result<PageDto<GoldConsumeStatisDto>> goldFunctionStatis(@RequestParam(required = false) Integer serverId,
                                                                    @RequestParam int startTime, @RequestParam int endTime,
                                                                    @RequestParam int pageNo, @RequestParam int pageSize) {

        // 查询所有的
        Page<GoldConsumeStatis> pageResult = null;
        if (serverId == null) {
            pageResult = consumeStatisService.findFunctionStatis(startTime, endTime, pageNo, pageSize);
        } else {
            pageResult = consumeStatisService.findFunctionStatis(startTime, endTime, serverId, pageNo, pageSize);
        }

        List<GoldConsumeStatisDto> dtos = consumeStatisDtoConverter.toGoldConsumeStatisDto(pageResult.getContent());

        PageDto<GoldConsumeStatisDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("markConsumeStatis:query")
    @GetMapping(value = "markConsumeStatis")
    @ApiOperation(value = "商城消耗统计")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
            @ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
            @ApiImplicitParam(name = "serverId", value = "服务器id，可为空", type = "integer")
    })
    @ServerRight(argsIndex = 0)
    public Result<PageDto<MarketConsumeStatisDto>> markConsumeStatis(@RequestParam(required = false) Integer serverId,
                                                                     @RequestParam int startTime, @RequestParam int endTime,
                                                                     @RequestParam int pageNo, @RequestParam int pageSize) {

        // 查询所有的
        Page<MarketStatis> pageResult = null;
        if (serverId == null) {
            pageResult = consumeStatisService.findMarketStatis(startTime, endTime, pageNo, pageSize);
        } else {
            pageResult = consumeStatisService.findMarketStatis(startTime, endTime, serverId, pageNo, pageSize);
        }

        List<MarketConsumeStatisDto> dtos = consumeStatisDtoConverter.toMarketConsumeStatisDtos(pageResult.getContent());

        PageDto<MarketConsumeStatisDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

}
