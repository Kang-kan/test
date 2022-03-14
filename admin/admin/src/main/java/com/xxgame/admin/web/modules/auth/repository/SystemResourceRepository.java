package com.xxgame.admin.web.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xxgame.admin.web.modules.auth.entity.SystemResource;

/**
 * 服务器资源定义
 * @author gil
 *
 */
public interface SystemResourceRepository extends JpaRepository<SystemResource, Integer> {

}
