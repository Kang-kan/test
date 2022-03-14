package com.xxgame.admin.web.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xxgame.admin.web.modules.auth.entity.SystemMenu;

/**
 * 菜单栏
 * @author gil
 *
 */
public interface SystemMenuRepository extends JpaRepository<SystemMenu, Integer> {
	
}
