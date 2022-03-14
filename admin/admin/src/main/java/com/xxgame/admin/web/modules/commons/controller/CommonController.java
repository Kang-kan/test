package com.xxgame.admin.web.modules.commons.controller;

import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.controller.model.SrvInfoDto;
import com.xxgame.admin.web.modules.commons.service.PropertyService;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("common")
@Api(tags = "常用功能")
public class CommonController extends BaseController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private GameServerService gameServerService;

    @RequiresUser
    @GetMapping(value = "getSrvInfo")
    @ApiOperation(value = "获取服务器信息")
    @ServerRight(argsIndex = 0)
    public Result<SrvInfoDto> getSrvInfo(@RequestParam int serverId) {
        if (serverId <= 0 || serverId > 999999) {
            return Result.error(ResultCode.PARAM_ERROR, "参数错误");
        }
        GameServer gameServer = gameServerService.getCacheGameServer(serverId);
        if (gameServer == null) {
            return Result.success(null);
        }
        SrvInfoDto dto = new SrvInfoDto();
        dto.setServerId(serverId);
        dto.setOpenTime(gameServer.getOpenTime());

        return Result.success(dto);
    }


}
