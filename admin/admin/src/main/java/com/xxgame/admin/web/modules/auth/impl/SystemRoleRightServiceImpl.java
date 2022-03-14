package com.xxgame.admin.web.modules.auth.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.xxgame.admin.web.modules.auth.repository.SystemRoleRightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xxgame.admin.web.modules.auth.SystemRoleRightService;
import com.xxgame.admin.web.modules.auth.entity.SystemRoleRight;

/**
 * 角色权限
 * 
 * @author gil
 *
 */
@Service
public class SystemRoleRightServiceImpl implements SystemRoleRightService {

	@Autowired
	private SystemRoleRightRepository systemRoleRightRepository;

	/**
	 * 权限描述
	 */
	private Set<String> rightAttrs = new HashSet<String>();

	@PostConstruct
	private void init() {
		rightAttrs.add("create");
		rightAttrs.add("delete");
		rightAttrs.add("update");
		rightAttrs.add("query");
	}

	/**
	 * 根据角色id找出所有权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	public List<SystemRoleRight> findByRoleId(int roleId) {
		return systemRoleRightRepository.findByRoleId(roleId);
	}

	@Override
	public Page<SystemRoleRight> findByRoleId(int roleId, int pageNo, int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		return systemRoleRightRepository.findByRoleId(roleId, pageRequest);
	}

	/**
	 * 根据roleId删除角色权限
	 * 
	 * @param roleId
	 */
	@Override
	public void deleteByRoleId(int roleId) {
		systemRoleRightRepository.deleteByRoleId(roleId);
	}

	@Override
	public void deleteByRoleIdAndResourceId(int roleId, int resourceId) {
		systemRoleRightRepository.deleteByRoleIdAndResourceId(roleId, resourceId);
	}

	/**
	 * 检查权限值是否正确
	 * 
	 * @param rights
	 * @return
	 */
	@Override
	public boolean valueCorrect(List<String> rights) {
		if (rights == null || rights.isEmpty()) {
			return true;
		}

		for (String right : rights) {
			if (!rightAttrs.contains(right)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 修改权限
	 * @param roleId
	 * @param resourceId
	 * @param rights
	 * @return
	 */
	@Override
	public SystemRoleRight updateRight(int roleId, int resourceId, List<String> rights) {
		SystemRoleRight systemRoleRight = systemRoleRightRepository.findByRoleIdAndResourceId(roleId, resourceId);
		if (systemRoleRight == null) {
			systemRoleRight = new SystemRoleRight();
			systemRoleRight.setRoleId(roleId);
			systemRoleRight.setResourceId(resourceId);
			systemRoleRight.setAuth("[]");
		}
		systemRoleRight.setAuth(JSON.toJSONString(rights));
		
		return systemRoleRightRepository.save(systemRoleRight);
	}

}