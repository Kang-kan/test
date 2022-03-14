package com.xxgame.admin.web.modules.user;

import com.xxgame.admin.web.modules.user.controller.model.SystemUserRequest;
import com.xxgame.admin.web.modules.user.entity.SystemUser;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

/**
 * 管理后台用户服务接口
 * 
 * @author gil
 *
 */
public interface SystemUserService {

	/**
	 * 登录
	 * 
	 * @param userName
	 * @param password md5(password + loginKey + salt)
	 * @param loginIp
	 * @return
	 */
	SystemUser login(String userName, String password, String loginIp);

	/**
	 * 获取SystemUser
	 * 
	 * @param id
	 * @return
	 */
	SystemUser getSystemUser(long id);

	/**
	 * 批量查找用户
	 * @param ids
	 * @return
	 */
	List<SystemUser> findUsers(Collection<Long> ids);

	/**
	 * 添加SystemUser
	 * 
	 * @param requestId
	 * @param request
	 * @return
	 */
	SystemUser add(long requestId, SystemUserRequest request);

	/**
	 * 更新SystemUser
	 * 
	 * @param requestId
	 * @param systemUser
	 * @return
	 */
	SystemUser update(long requestId, SystemUser systemUser);

	/**
	 * 修改密码
	 * 
	 * @param requestId
	 * @param oldPassword
	 * @param newPassword
	 */
	void changePassword(long requestId, String oldPassword, String newPassword);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	void resetPassword(long userId, String newPassword);

	/**
	 * 根据用户查找
	 * 
	 * @param userName
	 * @return
	 */
	SystemUser findByUserName(String userName);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(long id);

	/**
	 * 分页查找用户列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<SystemUser> userList(int pageNo, int pageSize);

	/**
	 * 根据账号名模糊查找
	 * @param userName
	 * @return
	 */
	List<SystemUser> findByUserNameLike(String userName);

	/**
	 * 根據手機號碼去尋找
	 * @param phone
	 */
	void getUserPhone(String phone);
}
