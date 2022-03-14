package com.xxgame.admin.web.modules.auth;

import java.util.*;
import java.util.stream.Collectors;

import com.xxgame.admin.web.modules.auth.controller.model.SystemMenuDto;
import com.xxgame.admin.web.modules.auth.controller.model.SystemResourceDto;
import com.xxgame.admin.web.modules.auth.controller.model.SystemRoleDto;
import com.xxgame.admin.web.modules.auth.entity.*;
import com.xxgame.admin.web.modules.user.controller.model.SimpleSystemUserDto;
import com.xxgame.admin.web.modules.user.controller.model.SystemUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.user.entity.SystemUser;

/**
 * 管理后台权限dto转换器
 * @author gil
 *
 */
@Service
public class SystemAdminDtoConverter {

	@Autowired
	private SystemResourceService systemResourceService;
	@Autowired
	private SystemUserRoleService systemUserRoleService;
	@Autowired
	private SystemRoleService systemRoleService;

	/**
	 * 转换成SystemUserDto
	 * @param systemUser
	 * @return
	 */
	public SystemUserDto toSystemUserDto(SystemUser systemUser) {
		SystemUserDto dto = new SystemUserDto();
		dto.setId(systemUser.getId() + "");
		dto.setUsername(systemUser.getUserName());
		dto.setName(systemUser.getName());
		dto.setPhone(systemUser.getPhone());
		dto.setEmail(systemUser.getEmail());
		dto.setStatus(systemUser.getStatus());
		dto.setSystemUser(systemUser.isSystemUser());
		dto.setCreateTime(systemUser.getCreateTime());

		Set<String> roleNames = new HashSet<>();
		List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(systemUser.getId());
		for (SystemUserRole userRole : userRoles) {
			SystemRole role = systemRoleService.getSystemRole(userRole.getRoleId());
			roleNames.add(role.getRoleName());
		}
		dto.setRoleNames(roleNames);

		return dto;
	}

	/**
	 * 转换成SystemUserDto列表
	 * @param systemUsers
	 * @return
	 */
	public List<SystemUserDto> toSystemUserDtos(List<SystemUser> systemUsers) {
		if (systemUsers == null || systemUsers.isEmpty()) {
			return new ArrayList<SystemUserDto>(1);
		}

		List<SystemUserDto> list = new ArrayList<SystemUserDto>();
		for (SystemUser user : systemUsers) {
			list.add(this.toSystemUserDto(user));
		}

		return list;
	}

	/**
	 * 转换成SystemMenuDto列表
	 * @param roleRights
	 * @return
	 */
	public List<SystemMenuDto> toMenuDtos(List<SystemRoleRight> roleRights) {
		List<SystemResource> resources = systemResourceService.getAllResources();

		// { menuId, SystemMenuDto }
		Map<Integer, SystemMenuDto> menuDtoMap = new HashMap<Integer, SystemMenuDto>();
		// { menuId, { resourceId, SystemResourceDto } }
		Map<Integer, Map<Integer, SystemResourceDto>> resourceDtoMap = new HashMap<Integer, Map<Integer, SystemResourceDto>>();

		// 系统定义的所有资源
		for (SystemResource resource : resources) {
			SystemMenuDto menuDto = menuDtoMap.get(resource.getMenuId());
			if (menuDto == null) {
				SystemMenu systemMenu = systemResourceService.getSystemMenu(resource.getMenuId());

				menuDto = new SystemMenuDto();
				menuDto.setId(resource.getMenuId());
				menuDto.setName(systemMenu.getName());

				menuDtoMap.put(resource.getMenuId(), menuDto);
			}

			Map<Integer, SystemResourceDto> resourceMap = resourceDtoMap.get(resource.getMenuId());
			if (resourceMap == null) {
				resourceMap = new HashMap<Integer, SystemResourceDto>();
				resourceDtoMap.put(resource.getMenuId(), resourceMap);
			}

			SystemResourceDto resourceDto = resourceMap.get(resource.getId());
			if (resourceDto == null) {
				resourceDto = new SystemResourceDto();
				resourceDto.setId(resource.getId());
				resourceMap.put(resource.getId(), resourceDto);
			}

			// 某个角色拥有的资源权限
			for (SystemRoleRight roleRight : roleRights) {
				// 找出是否是该资源的权限
				if (resource.getId() != roleRight.getResourceId()) {
					continue;
				}
				// 添加权限
				Set<String> authSet = JSON.parseObject(roleRight.getAuth(), new TypeReference<Set<String>>(){});
				resourceDto.getAuth().addAll(authSet);
			}
		}

		// 转换成前端所需要的结构
		List<SystemMenuDto> dtoList = new ArrayList<SystemMenuDto>();
		for (SystemMenuDto menuDto : menuDtoMap.values()) {
			Map<Integer, SystemResourceDto> resouceMap = resourceDtoMap.get(menuDto.getId());
			if (resouceMap == null) {
				continue;
			}
			for (SystemResourceDto systemResourceDto : resouceMap.values()) {
				if (systemResourceDto.getAuth() == null || systemResourceDto.getAuth().isEmpty()) {
					continue;
				}
				menuDto.getResource().add(systemResourceDto);
			}

			dtoList.add(menuDto);
		}
		dtoList = dtoList.stream().filter(dto -> dto.getResource().size() > 0).collect(Collectors.toList());

		return dtoList;
	}

	/**
	 * 转换成SystemRoleDto
	 * @param systemRole
	 * @return
	 */
	public SystemRoleDto toSystemRoleDto(SystemRole systemRole) {
		SystemRoleDto dto = new SystemRoleDto();
		dto.setId(systemRole.getId());
		dto.setRoleName(systemRole.getRoleName());
		dto.setRemark(systemRole.getRemark());
		dto.setSystemRole(systemRole.isSystemRole());
		dto.setOperator(systemRole.getOperator() + "");
		dto.setUpdateTime(systemRole.getUpdateTime());
		dto.setCreateTime(systemRole.getCreateTime());

		return dto;
	}

	/**
	 * 转换成SystemRoleDto列表
	 * @param systemRoles
	 * @return
	 */
	public List<SystemRoleDto> toSystemRoleDtos(List<SystemRole> systemRoles) {
		if (systemRoles == null || systemRoles.isEmpty()) {
			return new ArrayList<SystemRoleDto>(1);
		}

		List<SystemRoleDto> list = new ArrayList<SystemRoleDto>();
		for (SystemRole role : systemRoles) {
			list.add(this.toSystemRoleDto(role));
		}

		return list;
	}

	/**
	 * 转换成SystemResourceDto列表
	 * @param roleRights
	 * @return
	 */
	public List<SystemResourceDto> toSystemResourceDtos(List<SystemRoleRight> roleRights) {
		if (roleRights == null || roleRights.isEmpty()) {
			return new ArrayList<SystemResourceDto>(1);
		}

		// { resourceId, SystemResourceDto }
		Map<Integer, SystemResourceDto> resourceDtoMap = new HashMap<Integer, SystemResourceDto>();

		// 某个角色拥有的资源权限
		for (SystemRoleRight roleRight : roleRights) {
			SystemResourceDto resourceDto = resourceDtoMap.get(roleRight.getResourceId());
			if (resourceDto == null) {
				resourceDto = new SystemResourceDto();
				resourceDto.setId(roleRight.getResourceId());
				resourceDtoMap.put(roleRight.getResourceId(), resourceDto);
			}

			// 添加权限
			Set<String> authSet = JSON.parseObject(roleRight.getAuth(), new TypeReference<Set<String>>(){});
			resourceDto.getAuth().addAll(authSet);
		}

		return new ArrayList<SystemResourceDto>(resourceDtoMap.values());
	}

	/**
	 * 转换成SystemResourceDto
	 * @param systemRoleRight
	 * @return
	 */
	public SystemResourceDto toSystemResourceDto(SystemRoleRight systemRoleRight) {
		SystemResourceDto dto = new SystemResourceDto();
		dto.setId(systemRoleRight.getId());

		Set<String> authSet = JSON.parseObject(systemRoleRight.getAuth(), new TypeReference<Set<String>>(){});
		dto.setAuth(authSet);

		return dto;

	}

	/**
	 * 转换成SimpleSystemUserDto
	 * @param systemUser
	 * @return
	 */
	public SimpleSystemUserDto toSimpleSystemUserDto(SystemUser systemUser) {
		SimpleSystemUserDto dto = new SimpleSystemUserDto();
		dto.setId(systemUser.getId() + "");
		dto.setUsername(systemUser.getUserName());
		dto.setName(systemUser.getName());
		dto.setStatus(systemUser.getStatus());
		dto.setSystemUser(systemUser.isSystemUser());

		return dto;
	}

	/**
	 * 转换成SimpleSystemUserDto列表
	 * @param systemUsers
	 * @return
	 */
	public List<SimpleSystemUserDto> toSimpleSystemUserDtos(List<SystemUser> systemUsers) {
		if (systemUsers == null || systemUsers.isEmpty()) {
			return new ArrayList<>(0);
		}

		List<SimpleSystemUserDto> dtos = new ArrayList<>(systemUsers.size());
		for (SystemUser systemUser : systemUsers) {
			dtos.add(this.toSimpleSystemUserDto(systemUser));
		}
		return dtos;
	}

}