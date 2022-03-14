package com.xxgame.admin.web.modules.auth;

import java.util.List;

import com.xxgame.admin.web.modules.auth.entity.SystemRoleRight;
import org.springframework.data.domain.Page;

/**
 * 角色权限
 * 
 * @author gil
 *
 */
public interface SystemRoleRightService {

	/**
	 * 根据角色id找出所有权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<SystemRoleRight> findByRoleId(int roleId);

	/**
	 * 根据角色id分页查找权限
	 * @param roleId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<SystemRoleRight> findByRoleId(int roleId, int pageNo, int pageSize);

	/**
	 * 根据roleId删除角色权限
	 * 
	 * @param roleId
	 */
	void deleteByRoleId(int roleId);

	/**
	 * 根据roleId resourceId删除角色权限
	 *
	 * @param roleId
	 * @param resourceId
	 */
	void deleteByRoleIdAndResourceId(int roleId, int resourceId);

	/**
	 * 检查权限值是否正确
	 * 
	 * @param rights
	 * @return
	 */
	boolean valueCorrect(List<String> rights);

	/**
	 * 修改权限
	 * @param roleId
	 * @param resourceId
	 * @param rights
	 * @return
	 */
	SystemRoleRight updateRight(int roleId, int resourceId, List<String> rights);
	
}
