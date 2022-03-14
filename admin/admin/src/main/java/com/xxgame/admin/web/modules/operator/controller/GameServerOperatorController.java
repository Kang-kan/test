package com.xxgame.admin.web.modules.operator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;
import com.xxgame.admin.web.modules.commons.service.LoginSrvClient;
import com.xxgame.admin.web.modules.gameserver.controller.GameServerDtoConverter;
import com.xxgame.admin.web.modules.gameserver.controller.model.SimpleGameServerDto;
import com.xxgame.admin.web.modules.gameserver.entity.RoleGameServer;
import com.xxgame.admin.web.modules.gameserver.repository.RoleGameServerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("gsOperator")
@Api(tags = "服务器列表-1200")
public class GameServerOperatorController extends BaseController {

    @Autowired
    private RoleGameServerRepository roleGameServerRepository;
    @Autowired
    private SystemUserRoleService systemUserRoleService;
    @Autowired
    private GameServerDtoConverter gameServerDtoConverter;
    @Autowired
    private LoginSrvClient loginSrvClient;

    @RequiresPermissions("gsOperator:query")
    @GetMapping(value = "listServers")
    @ApiOperation(value = "获取我的服务器列表-1209")
    public Result<PageDto<SimpleGameServerDto>> listServers(@RequestParam int pageNo, @RequestParam int pageSize) {
        List<Integer> roleIds = new LinkedList<>();
        List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(this.getUserId());
        userRoles.forEach(r -> roleIds.add(r.getRoleId()));

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<RoleGameServer> pageResult = roleGameServerRepository.findByRoleIdIn(roleIds, pageRequest);

        List<SimpleGameServerDto> dtos = gameServerDtoConverter.toSimpleGameServerDtos(pageResult.getContent());

        PageDto<SimpleGameServerDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("gsOperator:update")
    @PostMapping(value = "setLoginServerStopDesc")
    @ApiOperation(value = "设置登录服关闭提示-1209")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "serverIds", value = "JSON格式服务器id列表", type = "json"),
            @ApiImplicitParam(name = "text", value = "提示内容", type = "string")
    })
    public Result<Void> setLoginServerStopDesc(@RequestParam String serverIds, @RequestParam String text) {
        if (StringUtils.isBlank(serverIds)) {
            return Result.error(ResultCode.PARAM_EMPTY, "服务器id列表为空");
        }
        if (StringUtils.isBlank(text)) {
            return Result.error(ResultCode.PARAM_EMPTY, "提示内容为空");
        }

        // 请求login服
        loginSrvClient.sendLoginServerStopDesc(JSON.parseObject(serverIds, new TypeReference<List<Integer>>(){}), text);

        return Result.success(null);
    }

}
