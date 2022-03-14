package com.xxgame.admin.web.modules.auth;

import java.util.List;

import com.xxgame.admin.web.modules.auth.controller.model.SystemRoleRequest;
import com.xxgame.admin.web.modules.auth.entity.SystemRole;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;
import org.springframework.data.domain.Page;

/**
 * 管理后台角色服务类
 * @author gil
 *
 */
public interface SystemRoleService {

	/**
	 * 添加SystemRole
	 * @param userId
	 * @param request
	 * @return
	 */
	SystemRole add(long userId, SystemRoleRequest request);
	
	/**
	 * 更新SystemRole
	 * @param userId
	 * @param request
	 * @param systemRole
	 * @return
	 */
	SystemRole update(long userId, SystemRoleRequest request, SystemRole systemRole);
	
	/**
	 * 是否已经有了这个角色名
	 * @param roleName
	 * @return
	 */
	long countByRoleName(String roleName);
	
	/**
	 * 查找SystemRole
	 * @param id
	 * @return
	 */
	SystemRole getSystemRole(int id);

	/**
	 * 分页查找用户的角色列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<SystemRole> getRoles(int pageNo, int pageSize);
    
	/**
	 * 删除角色
	 * @param id
	 */
	void deleteSystemRole(int id);
	
	/**
	 * 是否有权限查看订单身份证
	 * @param userRoles
	 * @return
	 */
	boolean viewOrderIdCard(List<SystemUserRole> userRoles);
	
}
