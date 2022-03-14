package com.xxgame.admin.web.modules.auth.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.auth.entity.SystemRole;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;
import com.xxgame.admin.web.modules.user.SystemUserService;
import com.xxgame.admin.web.modules.user.controller.model.SystemUserDto;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxgame.admin.web.modules.auth.SystemAdminDtoConverter;
import com.xxgame.admin.web.modules.auth.SystemRoleService;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.controller.model.SystemRoleDto;
import com.xxgame.admin.web.modules.user.entity.SystemUser;

/**
 * 角色用户关系接口
 * 
 * @author gil
 *
 */
@RestController
@RequestMapping("systemUserRole")
@Api(tags = "角色用户关系接口")
public class SystemUserRoleController extends BaseController {

	@Autowired
	private SystemRoleService systemRoleService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private SystemUserRoleService systemUserRoleService;
	@Autowired
	private SystemAdminDtoConverter systemAdminDtoConverter;

	/**
	 * 添加用户到指定的角色中
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@PostMapping(value = "add")
	@RequiresPermissions("systemUserRole:create")
	public Result<SystemRoleDto> add(@RequestParam Integer roleId, @RequestParam String userId) {
		if (roleId == null || roleId == 0 || StringUtils.isEmpty(userId)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		long longUserId = this.stringToLong(userId);

		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (systemRole == null) {
			return resultFactory.error(ResultCode.ROLE_NOT_EXISTS);
		}

		SystemUser systemUser = systemUserService.getSystemUser(longUserId);
		if (systemUser == null) {
			return resultFactory.error(ResultCode.USER_NOT_EXISTS);
		}

		SystemUserRole systemUserRole = systemUserRoleService.findByUserIdAndRoleId(longUserId, roleId);
		if (systemUserRole != null) {
			return resultFactory.error(ResultCode.HAVE_THIS_ROLE);
		}

		// 记录到数据库
		systemUserRole = systemUserRoleService.add(longUserId, roleId);
		SystemRoleDto dto = systemAdminDtoConverter.toSystemRoleDto(systemRole);

		return Result.success(dto);
	}

	/**
	 * 从指定的角色中删除用户，返回roleId
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@DeleteMapping(value = "delete")
	@RequiresPermissions("systemUserRole:delete")
	public Result<Integer> delete(@RequestParam Integer roleId, @RequestParam String userId) {
		if (roleId == null || roleId == 0 || StringUtils.isEmpty(userId)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		long longUserId = this.stringToLong(userId);

		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (null == systemRole) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		SystemUser systemUser = systemUserService.getSystemUser(longUserId);
		if (systemUser == null) {
			return resultFactory.error(ResultCode.USER_NOT_EXISTS);
		}
		if (systemUser.isSystemUser() && systemRole.isSystemRole()) {
			return resultFactory.error(ResultCode.SYSTEM_ROLE);
		}

		SystemUserRole systemUserRole = systemUserRoleService.findByUserIdAndRoleId(longUserId, roleId);
		if (systemUserRole == null) {
			return resultFactory.error(ResultCode.DONT_HAVE_THIS_ROLE);
		}

		systemUserRoleService.delete(systemUserRole.getId());

		return Result.success(roleId);
	}

	/**
	 * 获取指定用户的所有的角色列表
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "getRolesByUser/{userId}")
	@RequiresPermissions("systemUserRole:query")
	public Result<Set<SystemRoleDto>> getRolesByUser(@PathVariable String userId) {
		if (StringUtils.isEmpty(userId)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		long longUserId = this.stringToLong(userId);

		SystemUser systemUser = systemUserService.getSystemUser(longUserId);
		if (systemUser == null) {
			return resultFactory.error(ResultCode.USER_NOT_EXISTS);
		}

		List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(longUserId);
		if (userRoles == null) {
			return Result.success(null);
		}

		Set<SystemRoleDto> dtos = new HashSet<SystemRoleDto>();
		for (SystemUserRole userRole : userRoles) {
			SystemRole systemRole = systemRoleService.getSystemRole(userRole.getRoleId());
			SystemRoleDto dto = systemAdminDtoConverter.toSystemRoleDto(systemRole);

			dtos.add(dto);
		}

		return Result.success(dtos);
	}

	/**
	 * 获取指定角色的所有的用户列表
	 * @param roleId
	 * @return
	 */
	@GetMapping(value = "getUsersByRole/{roleId}")
	@RequiresPermissions("systemUserRole:query")
	public Result<PageDto<SystemUserDto>> getUsersByRole(@PathVariable Integer roleId) {
		if (roleId == null || roleId == 0) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (null == systemRole) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}

		List<SystemUserRole> userRoles = systemUserRoleService.findByRoleId(roleId);
		if (userRoles == null) {
			return Result.success(null);
		}
		List<Long> userIds = new ArrayList<>(userRoles.size());
		userRoles.forEach(userRole -> userIds.add(userRole.getUserId()));

		List<SystemUser> systemUsers = systemUserService.findUsers(userIds);
		List<SystemUserDto> dtos = systemAdminDtoConverter.toSystemUserDtos(systemUsers);

		PageDto<SystemUserDto> pageDto = new PageDto<SystemUserDto>();
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	/**
	 * 获取我的角色列表
	 * @return
	 */
	@GetMapping(value = "getMyRoles")
	public Result<Set<SystemRoleDto>> getMyRoles() {
		long userId = this.getUserId();

		List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(userId);
		if (userRoles == null) {
			return Result.success(null);
		}

		Set<SystemRoleDto> dtos = new HashSet<SystemRoleDto>();
		for (SystemUserRole userRole : userRoles) {
			SystemRole systemRole = systemRoleService.getSystemRole(userRole.getRoleId());
			SystemRoleDto dto = systemAdminDtoConverter.toSystemRoleDto(systemRole);

			dtos.add(dto);
		}

		return Result.success(dtos);
	}

}