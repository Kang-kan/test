package com.xxgame.admin.web.modules.auth.controller;

import java.util.ArrayList;
import java.util.List;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.auth.entity.SystemResource;
import com.xxgame.admin.web.modules.auth.entity.SystemRole;
import com.xxgame.admin.web.modules.auth.entity.SystemRoleRight;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.xxgame.admin.web.modules.auth.SystemAdminDtoConverter;
import com.xxgame.admin.web.modules.auth.SystemResourceService;
import com.xxgame.admin.web.modules.auth.SystemRoleRightService;
import com.xxgame.admin.web.modules.auth.SystemRoleService;
import com.xxgame.admin.web.modules.auth.controller.model.SystemResourceDto;

/**
 * 角色权限接口
 * @author gil
 *
 */
@RestController
@RequestMapping("systemRoleRight")
@Api(tags = "角色权限接口")
public class SystemRoleRightController extends BaseController {

	@Autowired
	private SystemRoleService systemRoleService;
	@Autowired
	private SystemAdminDtoConverter systemAdminDtoConverter;
	@Autowired
	private SystemRoleRightService systemRoleRightService;
	@Autowired
	private SystemResourceService systemResourceService;

	/**
	 * 获取角色拥有的权限列表
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("systemRoleRight:query")
	@GetMapping(value = "getRights/{roleId}")
	public Result<PageDto<SystemResourceDto>> getRights(@PathVariable Integer roleId) {
		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (systemRole == null) {
			return resultFactory.error(ResultCode.ROLE_NOT_EXISTS);
		}

		List<SystemRoleRight> systemRoleRights = systemRoleRightService.findByRoleId(roleId);
		List<SystemResourceDto> dtos = systemAdminDtoConverter.toSystemResourceDtos(systemRoleRights);

		PageDto<SystemResourceDto> pageDto = new PageDto<SystemResourceDto>();
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	/**
	 * 修改资源权限到角色，没有权限就传空列表[]
	 * @param roleId
	 * @param resourceId
	 * @param rights
	 * @return
	 */
	@RequiresPermissions("systemRoleRight:update")
	@PutMapping(value = "updateRight/{roleId}/{resourceId}")
	public Result<SystemResourceDto> updateRight(@PathVariable Integer roleId, @PathVariable Integer resourceId, @RequestBody List<String> rights) {
		if (rights == null) {
			rights = new ArrayList<String>(1);
		}

		boolean valueCorrect = systemRoleRightService.valueCorrect(rights);
		if (!valueCorrect) {
			return resultFactory.error(ResultCode.RIGHT_NOT_CORRECT);
		}

		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (systemRole == null) {
			return resultFactory.error(ResultCode.ROLE_NOT_EXISTS);
		}
		if (systemRole.isSystemRole()) {
			return resultFactory.error(ResultCode.SYSTEM_ROLE);
		}

		SystemResource systemResource = systemResourceService.getSystemResource(resourceId);
		if (systemResource == null) {
			return resultFactory.error(ResultCode.RESOURCE_NOT_EXISTS);
		}

		SystemRoleRight systemRoleRight = systemRoleRightService.updateRight(roleId, resourceId, rights);
		SystemResourceDto dto = systemAdminDtoConverter.toSystemResourceDto(systemRoleRight);

		return Result.success(dto);
	}

	/**
	 * 删除角色权限
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	@RequiresPermissions("systemRoleRight:delete")
	@DeleteMapping (value = "delete/{roleId}/{resourceId}")
	@Transactional(rollbackFor = Exception.class)
	public Result<Integer> delete(@PathVariable Integer roleId, @PathVariable Integer resourceId) {
		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (systemRole == null) {
			return resultFactory.error(ResultCode.ROLE_NOT_EXISTS);
		}
		if (systemRole.isSystemRole()) {
			return resultFactory.error(ResultCode.SYSTEM_ROLE);
		}

		SystemResource systemResource = systemResourceService.getSystemResource(resourceId);
		if (systemResource == null) {
			return resultFactory.error(ResultCode.RESOURCE_NOT_EXISTS);
		}

		systemRoleRightService.deleteByRoleIdAndResourceId(roleId, resourceId);
		return Result.success(resourceId);
	}

}