package com.xxgame.admin.web.modules.auth;

import java.util.List;

import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;

/**
 * 管理后台用户角色关系服务类
 * 
 * @author gil
 *
 */
public interface SystemUserRoleService {

	/**
	 * 根据用户id找出所有角色
	 * 
	 * @param userId
	 * @return
	 */
	List<SystemUserRole> findByUserId(long userId);

	/**
	 * 根据角色id找出所有用户角色
	 *
	 * @param roleId
	 * @return
	 */
	List<SystemUserRole> findByRoleId(int roleId);

	/**
	 * 根据用户id和角色id查找
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	SystemUserRole findByUserIdAndRoleId(long userId, int roleId);

	/**
	 * 增加用户角色关系
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	SystemUserRole add(long userId, int roleId);

	/**
	 * 根据roleId删除用户对应关系
	 * 
	 * @param roleId
	 */
	void deleteByRoleId(int roleId);

	/**
	 * 根据userId删除用户对应关系
	 * 
	 * @param userId
	 */
	void deleteByUserId(long userId);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(long id);

}
