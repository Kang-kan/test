package com.xxgame.admin.web.modules.auth.controller;

import java.util.List;

import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.modules.auth.controller.model.SystemRoleRequest;
import com.xxgame.admin.web.modules.auth.entity.SystemRole;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxgame.admin.web.modules.auth.SystemAdminDtoConverter;
import com.xxgame.admin.web.modules.auth.SystemRoleRightService;
import com.xxgame.admin.web.modules.auth.SystemRoleService;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.controller.model.SystemRoleDto;

/**
 * 角色接口
 * @author gil
 *
 */
@RestController
@RequestMapping("systemRole")
@Api(tags = "角色接口")
public class SystemRoleController extends BaseController {

	@Autowired
	private SystemRoleService systemRoleService;
	@Autowired
	private SystemAdminDtoConverter systemAdminDtoConverter;
	@Autowired
	private SystemUserRoleService systemUserRoleService;
	@Autowired
	private SystemRoleRightService systemRoleRightService;

	/**
	 * 添加角色的接口
	 * @param request
	 * @return
	 */
	@RequiresPermissions("systemRole:create")
	@PostMapping(value = "add")
	@Transactional(rollbackFor = Exception.class)
	public Result<SystemRoleDto> add(@RequestBody SystemRoleRequest request) {
		// 参数检查
		checkParameter(request);

		long userId = this.getUserId();
		SystemRole systemRole = systemRoleService.add(userId, request);
		SystemRoleDto dto = systemAdminDtoConverter.toSystemRoleDto(systemRole);

		return Result.success(dto);
	}

	/**
	 * 参数检查
	 * @param request
	 */
	private void checkParameter(SystemRoleRequest request) {
		if (request == null) {
			throw new BusinessException(ResultCode.PARAM_EMPTY);
		}
		if (StringUtils.isEmpty(request.getRoleName()) || request.getRoleName().length() <= 3) {
			throw new BusinessException(ResultCode.PARAM_MIN_LENGTH);
		}
		if (request.getRoleName().length() >= 32) {
			throw new BusinessException(ResultCode.PARAM_MAX_LENGTH);
		}
		if (StringUtils.isEmpty(request.getRemark()) && request.getRemark().length() >= 128) {
			throw new BusinessException(ResultCode.PARAM_MAX_LENGTH);
		}
		
		long countByRoleName = systemRoleService.countByRoleName(request.getRoleName());
		if (countByRoleName > 0) {
			throw new BusinessException(ResultCode.ROLE_NAME_EXISTS);
		}
	}

	/**
	 * 删除角色的接口，返回roleId
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("systemRole:delete")
	@PostMapping(value = "delete/{roleId}")
	@Transactional(rollbackFor = Exception.class)
	public Result<Integer> delete(@PathVariable Integer roleId) {
		if (roleId == null || roleId == 0) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}

		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (null == systemRole) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		// 系统管理员角色不能删除
		if (systemRole.isSystemRole()) {
			return resultFactory.error(ResultCode.SYSTEM_ROLE);
		}
		
		// 删除角色对应用户关系
		systemUserRoleService.deleteByRoleId(roleId);
		// 删除角色对应权限
		systemRoleRightService.deleteByRoleId(roleId);
		// 删除角色
		systemRoleService.deleteSystemRole(roleId);

		return Result.success(roleId);
	}

	/**
	 * 更新角色的接口
	 * @param request
	 * @return
	 */
	@RequiresPermissions("systemRole:update")
	@PostMapping(value = "update")
	@Transactional(rollbackFor = Exception.class)
	public Result<SystemRoleDto> update(@RequestBody SystemRoleRequest request) {
		// 参数检查
		checkParameter(request);
		if (request.getId() <= 0) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}

		SystemRole systemRole = systemRoleService.getSystemRole(request.getId());
		if (null == systemRole) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		if (systemRole.isSystemRole()) {
			return resultFactory.error(ResultCode.SYSTEM_ROLE);
		}
		
		long countByRoleName = systemRoleService.countByRoleName(request.getRoleName());
		// 有一个相同的名，已被别人占用了
		if (countByRoleName >= 1 && !systemRole.getRoleName().equalsIgnoreCase(request.getRoleName())) {
			return resultFactory.error(ResultCode.ROLE_NAME_EXISTS);
		}

		long userId = this.getUserId();
		systemRole = systemRoleService.update(userId, request, systemRole);
		SystemRoleDto dto = systemAdminDtoConverter.toSystemRoleDto(systemRole);

		return Result.success(dto);
	}

	/**
	 * 查询所有角色，支持分页
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequiresPermissions("systemRole:query")
	@GetMapping(value = "roleList")
	public Result<PageDto<SystemRoleDto>> roleList(@RequestParam int pageNo, @RequestParam int pageSize) {
		if (pageSize >= this.SMALL_LIST_SIZE) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}

		Page<SystemRole> pageResult = systemRoleService.getRoles(pageNo, pageSize);
		List<SystemRoleDto> dtos = systemAdminDtoConverter.toSystemRoleDtos(pageResult.getContent());

		PageDto<SystemRoleDto> pageDto = new PageDto<SystemRoleDto>();
		pageDto.setContents(dtos);
		this.setPageInfo(pageDto, pageResult);

		return Result.success(pageDto);
	}

	/**
	 * 根据id查询角色
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("systemRole:query")
	@GetMapping(value = "getRole/{roleId}")
	public Result<SystemRoleDto> getRole(@PathVariable Integer roleId) {
		if (roleId == null || roleId == 0) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		
		SystemRole systemRole = systemRoleService.getSystemRole(roleId);
		if (systemRole == null) {
			return resultFactory.error(ResultCode.RESULT_EMPTY);
		}
		SystemRoleDto dto = systemAdminDtoConverter.toSystemRoleDto(systemRole);

		return Result.success(dto);
	}

}