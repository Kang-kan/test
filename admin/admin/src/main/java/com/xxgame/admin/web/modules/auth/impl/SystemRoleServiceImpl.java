package com.xxgame.admin.web.modules.auth.impl;

import java.util.List;

import com.xxgame.admin.web.modules.auth.repository.SystemRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xxgame.admin.web.modules.auth.SystemRoleService;
import com.xxgame.admin.web.modules.auth.controller.model.SystemRoleRequest;
import com.xxgame.admin.web.modules.auth.entity.SystemRole;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;

/**
 * 管理后台角色服务类
 * @author gil
 *
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleRepository systemRoleRepository;
    
	/**
	 * 添加SystemRole
	 * @param request
	 * @return
	 */
    @Override
	public SystemRole add(long userId, SystemRoleRequest request) {
		SystemRole systemRole = new SystemRole();
		
		systemRole = new SystemRole();
        systemRole.setCreateTime(System.currentTimeMillis());

        return update(userId, request, systemRole);
	}
	
	/**
	 * 更新SystemRole
	 * @param userId
	 * @param request
	 * @param systemRole
	 * @return
	 */
    @Override
	public SystemRole update(long userId, SystemRoleRequest request, SystemRole systemRole) {
		systemRole.setRoleName(request.getRoleName());
        
        String remark = request.getRemark();
        if (StringUtils.isEmpty(remark)) {
        	remark = "";
        }
        systemRole.setRemark(remark);
        systemRole.setOperator(userId);
        systemRole.setUpdateTime(System.currentTimeMillis());
        
        return systemRoleRepository.save(systemRole);
	}
	
	/**
	 * 是否已经有了这个角色名
	 * @param roleName
	 * @return
	 */
    @Override
	public long countByRoleName(String roleName) {
		return systemRoleRepository.countByRoleName(roleName);
	}
	
	/**
	 * 查找SystemRole
	 * @param id
	 * @return
	 */
    @Override
	public SystemRole getSystemRole(int id) {
		return systemRoleRepository.findById(id).get();
	}

	/**
	 * 分页查找用户的角色列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<SystemRole> getRoles(int pageNo, int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		return systemRoleRepository.findAll(pageRequest);
	}
	
	/**
	 * 删除角色
	 * @param id
	 */
    @Override
	public void deleteSystemRole(int id) {
		systemRoleRepository.deleteById(id);
	}
	
	/**
	 * 是否有权限查看订单身份证
	 * @param userRoles
	 * @return
	 */
    @Override
	public boolean viewOrderIdCard(List<SystemUserRole> userRoles) {
		if (userRoles == null || userRoles.isEmpty()) {
			return false;
		}
		
		for (SystemUserRole userRole : userRoles) {
			SystemRole systemRole = systemRoleRepository.findById(userRole.getRoleId()).get();
			if (systemRole != null) {
				if (systemRole.isSystemRole() || systemRole.getRoleName().equals("查看订单身份证")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}