package com.xxgame.admin.web.modules.auth.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xxgame.admin.web.modules.auth.entity.SystemRoleRight;

/**
 * 角色权限
 * 
 * @author gil
 *
 */
public interface SystemRoleRightRepository extends JpaRepository<SystemRoleRight, Long> {

	/**
	 * 根据角色id找出所有权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<SystemRoleRight> findByRoleId(int roleId);

	/**
	 * 根据角色id找出所有权限
	 *
	 * @param roleId
	 * @param pageable
	 * @return
	 */
	Page<SystemRoleRight> findByRoleId(int roleId, Pageable pageable);

	/**
	 * 根据roleId删除角色权限
	 * 
	 * @param roleId
	 */
	@Modifying
	@Query("DELETE FROM system_role_right e WHERE e.roleId = ?1")
	void deleteByRoleId(int roleId);

	/**
	 * 根据roleId resourceId删除角色权限
	 *
	 * @param roleId
	 * @param resourceId
	 */
	@Modifying
	@Query("DELETE FROM system_role_right e WHERE e.roleId = ?1 AND e.resourceId = ?2")
	void deleteByRoleIdAndResourceId(int roleId, int resourceId);

	/**
	 * 根据角色id和资源id查找
	 * 
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	SystemRoleRight findByRoleIdAndResourceId(int roleId, int resourceId);
	
}
