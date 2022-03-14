package com.xxgame.admin.web.modules.gameserver.controller;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.auth.SystemRoleService;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.entity.SystemRole;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.controller.model.SimpleGameServerDto;
import com.xxgame.admin.web.modules.gameserver.controller.model.UserGameServerDto;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.entity.RoleGameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import com.xxgame.admin.web.modules.gameserver.repository.RoleGameServerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 角色服务器管理接口
 * @author gil
 *
 */
@RestController
@RequestMapping("roleGameServer")
@Api(tags = "角色服务器管理-206")
public class RoleGameServerController extends BaseController {

	@Autowired
	private RoleGameServerRepository roleGameServerRepository;
	@Autowired
	private GameServerRepository gameServerRepository;
	@Autowired
	private GameServerDtoConverter gameServerDtoConverter;
	@Autowired
	private GameServerService gameServerService;
	@Autowired
	private SystemRoleService systemRoleService;
	@Autowired
	private SystemUserRoleService systemUserRoleService;

	@RequiresPermissions("roleGameServer:query")
	@GetMapping(value = "listServers/{roleId}")
	@ApiOperation(value = "获取角色拥有的服务器列表-206")
	public Result<PageDto<UserGameServerDto>> listServers(@PathVariable int roleId, @RequestParam int pageNo, @RequestParam int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		Page<RoleGameServer> pageResult = roleGameServerRepository.findByRoleId(roleId, pageRequest);

		List<UserGameServerDto> dtos = gameServerDtoConverter.toUserGameServerDtos(pageResult.getContent());

		PageDto<UserGameServerDto> pageDto = new PageDto<>();
		pageDto.setContents(dtos);
		this.setPageInfo(pageDto, pageResult);

		return Result.success(pageDto);
	}

	@RequiresPermissions("roleGameServer:create")
	@PutMapping(value = "addServer/{roleId}")
	@ApiOperation(value = "增加服务器-206")
	public Result<UserGameServerDto> addServer(@PathVariable int roleId, @RequestParam Integer serverId) {
		if (serverId == null || serverId == 0 || roleId == 0) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}

		Optional<GameServer> optional = gameServerRepository.findById(serverId);
		if (!optional.isPresent()) {
			return Result.error(ResultCode.PARAM_ERROR, "服务器不存在");
		}

		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (systemRole == null) {
			return Result.error(ResultCode.USER_NOT_EXISTS, "角色不存在");
		}

		RoleGameServer roleGameServer = roleGameServerRepository.findByRoleIdAndServerId(roleId, serverId);
		if (roleGameServer != null) {
			return Result.error(ResultCode.PARAM_ERROR, "服务器已存在");
		}

		// 记录到数据库
		roleGameServer = gameServerService.addRoleGameServedr(roleId, serverId);
		UserGameServerDto dto = gameServerDtoConverter.toUserGameServerDto(roleGameServer);

		return Result.success(dto);
	}

	@RequiresPermissions("roleGameServer:delete")
	@DeleteMapping(value = "removeServer/{roleId}")
	@ApiOperation(value = "删除服务器-206")
	public Result<Long> removeServer(@PathVariable int roleId, @RequestParam Integer serverId) {
		if (serverId == null || serverId == 0 || roleId == 0) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}

		Optional<GameServer> optional = gameServerRepository.findById(serverId);
		if (!optional.isPresent()) {
			return Result.error(ResultCode.PARAM_ERROR, "服务器不存在");
		}

		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (systemRole == null) {
			return Result.error(ResultCode.USER_NOT_EXISTS, "角色不存在");
		}

		RoleGameServer roleGameServer = roleGameServerRepository.findByRoleIdAndServerId(roleId, serverId);
		if (roleGameServer == null) {
			return Result.error(ResultCode.PARAM_ERROR, "服务器不存在");
		}

		roleGameServerRepository.deleteById(roleGameServer.getId());

		return Result.success(roleGameServer.getId());
	}

	@RequiresUser
	@GetMapping(value = "getMyServers")
	@ApiOperation(value = "获取我的服务器列表-206")
	public Result<List<SimpleGameServerDto>> getMyServers() {
		long requestId = this.getUserId();
		if (requestId <= 0) {
			return resultFactory.error(ResultCode.TOKEN_EXPIRE);
		}

		List<Integer> roleIds = new LinkedList<>();
		List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(this.getUserId());
		userRoles.forEach(r -> roleIds.add(r.getRoleId()));

		List<RoleGameServer> roleGameServers = roleGameServerRepository.findByRoleIdIn(roleIds);

		List<SimpleGameServerDto> dtos = gameServerDtoConverter.toSimpleGameServerDtos(roleGameServers);
		Collections.sort(dtos, new Comparator<SimpleGameServerDto>() {
			@Override
			public int compare(SimpleGameServerDto o1, SimpleGameServerDto o2) {
				Integer s1 = o1.getId();
				Integer s2 = o2.getId();
				return s1.compareTo(s2);
			}
		});

		System.out.println(dtos.get(0).toString());
		return Result.success(dtos);
	}

}