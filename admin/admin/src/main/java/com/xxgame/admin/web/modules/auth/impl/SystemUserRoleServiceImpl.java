package com.xxgame.admin.web.modules.auth.impl;

import java.util.List;

import com.xxgame.admin.web.modules.auth.repository.SystemUserRoleRepository;
import com.xxgame.admin.web.modules.commons.IdWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;

/**
 * 管理后台用户角色关系服务类
 * 
 * @author gil
 *
 */
@Service
public class SystemUserRoleServiceImpl implements SystemUserRoleService {

	@Autowired
	private SystemUserRoleRepository systemUserRoleRepository;
	@Autowired
	private IdWorkerService idWorkerService;

	/**
	 * 根据用户id找出所有角色
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<SystemUserRole> findByUserId(long userId) {
		return systemUserRoleRepository.findByUserId(userId);
	}

	@Override
	public List<SystemUserRole> findByRoleId(int roleId) {
		return systemUserRoleRepository.findByRoleId(roleId);
	}

	/**
	 * 根据用户id和角色id查找
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Override
	public SystemUserRole findByUserIdAndRoleId(long userId, int roleId) {
		return systemUserRoleRepository.findByUserIdAndRoleId(userId, roleId);
	}

	/**
	 * 增加用户角色关系
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Override
	public SystemUserRole add(long userId, int roleId) {
		SystemUserRole systemUserRole = new SystemUserRole();
		systemUserRole.setId(idWorkerService.nextCommonId());
		systemUserRole.setUserId(userId);
		systemUserRole.setRoleId(roleId);

		return systemUserRoleRepository.save(systemUserRole);
	}

	/**
	 * 根据roleId删除用户对应关系
	 * 
	 * @param roleId
	 */
	@Override
	public void deleteByRoleId(int roleId) {
		systemUserRoleRepository.deleteByRoleId(roleId);
	}
	
	 /**
	 * 根据userId删除用户对应关系
	 * @param userId
	 */
	@Override
	 public void deleteByUserId(long userId) {
		 systemUserRoleRepository.deleteByUserId(userId);
	 }

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	public void delete(long id) {
		systemUserRoleRepository.deleteById(id);
	}

}