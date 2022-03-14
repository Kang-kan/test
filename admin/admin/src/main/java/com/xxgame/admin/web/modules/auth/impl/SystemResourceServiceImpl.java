package com.xxgame.admin.web.modules.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxgame.admin.web.modules.auth.SystemResourceService;
import com.xxgame.admin.web.modules.auth.entity.SystemMenu;
import com.xxgame.admin.web.modules.auth.entity.SystemResource;
import com.xxgame.admin.web.modules.auth.repository.SystemMenuRepository;
import com.xxgame.admin.web.modules.auth.repository.SystemResourceRepository;

/**
 * 系统资源服务类
 * @author gil
 *
 */
@Service
public class SystemResourceServiceImpl implements SystemResourceService {

    @Autowired
    private SystemMenuRepository systemMenuRepository;
    @Autowired
    private SystemResourceRepository systemResourceRepository;
    
    /**
     * 获取所有菜单栏
     * @return
     */
    @Override
	public List<SystemMenu> getAllMenus() {
		return systemMenuRepository.findAll();
	}
	
	/**
	 * 获取SystemMenu
	 * @param id
	 * @return
	 */
    @Override
	public SystemMenu getSystemMenu(int id) {
		return systemMenuRepository.findById(id).get();
	}
    
    /**
     * 获取所有菜单栏
     * @return
     */
    @Override
	public List<SystemResource> getAllResources() {
		return systemResourceRepository.findAll();
	}
	
	/**
	 * 获取SystemResource
	 * @param id
	 * @return
	 */
    @Override
	public SystemResource getSystemResource(int id) {
		return systemResourceRepository.findById(id).get();
	}
	
}
