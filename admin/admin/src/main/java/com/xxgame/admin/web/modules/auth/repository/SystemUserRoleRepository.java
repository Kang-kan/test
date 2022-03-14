package com.xxgame.admin.web.modules.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;

/**
 * 用户角色关系
 * @author gil
 *
 */
public interface SystemUserRoleRepository extends JpaRepository<SystemUserRole, Long> {

	/**
	 * 根据用户id找出所有角色
	 * @param userId
	 * @return
	 */
    List<SystemUserRole> findByUserId(long userId);

	/**
	 * 根据角色id找出所有用户角色
	 * @param roleId
	 * @return
	 */
	List<SystemUserRole> findByRoleId(int roleId);

    /**
     * 根据用户id和角色id查找
     * @param userId
     * @param roleId
     * @return
     */
    SystemUserRole findByUserIdAndRoleId(long userId, int roleId);
    
	 /**
	 * 根据roleId删除用户对应关系
	 * @param roleId
	 */
	 @Modifying
	 @Query("DELETE FROM system_user_role e WHERE e.roleId = ?1")
	 void deleteByRoleId(int roleId);
    
	 /**
	 * 根据userId删除用户对应关系
	 * @param userId
	 */
	 @Modifying
	 @Query("DELETE FROM system_user_role e WHERE e.userId = ?1")
	 void deleteByUserId(long userId);
	 
}
