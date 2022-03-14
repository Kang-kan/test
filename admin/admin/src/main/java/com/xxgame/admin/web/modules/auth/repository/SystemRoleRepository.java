package com.xxgame.admin.web.modules.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xxgame.admin.web.modules.auth.entity.SystemRole;

/**
 * 角色
 * @author gil
 *
 */
public interface SystemRoleRepository extends JpaRepository<SystemRole, Integer> {
	
    /**
     * 查询是否存在
     * @param id
     * @return
     */
    @Query("SELECT COUNT(*) FROM system_role e WHERE e.roleName=?1")
    long countByRoleName(String roleName);
	
	/**
	 * 分页查找用户的角色列表
	 * @param lastId
	 * @param size
	 * @return
	 */
	@Query(value = "SELECT r.* FROM system_role r WHERE r.id < ?1 ORDER BY r.id DESC LIMIT ?2", nativeQuery = true)
	List<SystemRole> getRoles(int lastId, int size);
	
}
