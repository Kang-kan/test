package com.xxgame.admin.web.modules.auth;

import java.util.List;

import com.xxgame.admin.web.modules.auth.entity.SystemMenu;
import com.xxgame.admin.web.modules.auth.entity.SystemResource;

/**
 * 系统资源服务
 * @author gil
 *
 */
public interface SystemResourceService {

    /**
     * 获取所有菜单栏
     * @return
     */
	List<SystemMenu> getAllMenus();
	
	/**
	 * 获取SystemMenu
	 * @param id
	 * @return
	 */
	SystemMenu getSystemMenu(int id);
	
    /**
     * 获取所有菜单栏
     * @return
     */
	List<SystemResource> getAllResources();
	
	/**
	 * 获取SystemResource
	 * @param id
	 * @return
	 */
	SystemResource getSystemResource(int id);
	
}
