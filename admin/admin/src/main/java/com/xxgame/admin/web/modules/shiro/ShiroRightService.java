package com.xxgame.admin.web.modules.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.auth.SystemResourceService;
import com.xxgame.admin.web.modules.auth.SystemRoleRightService;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.entity.SystemResource;
import com.xxgame.admin.web.modules.auth.entity.SystemRoleRight;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;

/**
 * 管理后台shiro权限服务类
 * @author gil
 *
 */
@Service
public class ShiroRightService {
	
	@Autowired
	private SystemUserRoleService systemUserRoleService;
	@Autowired
	private SystemRoleRightService systemRoleRightService;
	@Autowired
	private SystemResourceService systemResourceService;
	
	/**
	 * 获取用户的所有权限
	 * @param systemUserId
	 * @return
	 */
	public Set<String> getRights(long systemUserId) {
		Set<String> resultSet = new HashSet<String>();
		// 获取角色
		List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(systemUserId);
		if (userRoles == null || userRoles.isEmpty()) {
			return resultSet;
		}
		
		// 多个角色的权限集合，可能有重复的
		for (SystemUserRole userRole : userRoles) {
			// 每个角色又有多个权限
			List<SystemRoleRight> roleRights = systemRoleRightService.findByRoleId(userRole.getRoleId());
			if (roleRights == null || roleRights.isEmpty()) {
				continue;
			}
			
			for (SystemRoleRight roleRight : roleRights) {
				SystemResource systemResource = systemResourceService.getSystemResource(roleRight.getResourceId());
				if (systemResource == null) {
					continue;
				}
				
				Set<String> authSet = JSON.parseObject(roleRight.getAuth(), new TypeReference<Set<String>>(){});
				// 加模块名
				for (String auth : authSet) {
					String shiroRight = String.format("%s:%s", systemResource.getModuleName(), auth);
					resultSet.add(shiroRight);
				}
			}
		}
		
		return resultSet;
	}

}
