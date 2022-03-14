package com.xxgame.admin.web.modules.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.modules.auth.SystemRoleService;
import com.xxgame.admin.web.modules.auth.controller.model.SystemMenuDto;
import com.xxgame.admin.web.modules.auth.entity.SystemRole;
import com.xxgame.admin.web.modules.commons.cache.CacheKeys;
import com.xxgame.admin.web.modules.commons.cache.LocalCacheService;
import com.xxgame.admin.web.modules.user.TokenService;
import com.xxgame.admin.web.modules.user.controller.model.*;
import com.xxgame.admin.web.util.CryptUtils;
import com.xxgame.admin.web.util.HttpUtils;
import com.xxgame.admin.web.util.Md5DigestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.admin.web.modules.auth.SystemAdminDtoConverter;
import com.xxgame.admin.web.modules.auth.SystemRoleRightService;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.entity.SystemRoleRight;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;
import com.xxgame.admin.web.modules.user.SystemUserService;
import com.xxgame.admin.web.modules.user.entity.SystemUser;

/**
 * 管理后台用户接口
 * @author gil
 *
 */
@RestController
@RequestMapping("systemuser")
@Api(tags = "管理后台用户接口")
public class SystemUserController extends BaseController {
	
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private SystemUserRoleService systemUserRoleService;
	@Autowired
	private SystemRoleRightService systemRoleRightService;
	@Autowired
	private SystemAdminDtoConverter systemAdminDtoConverter;
	@Autowired
	private SystemRoleService systemRoleService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private LocalCacheService localCacheService;

	@Value("${social.loginKey}")
	private String loginKey;
	@Value("${social.loginIv}")
	private String loginIv;

	// 登录参数过期时间10分钟
	private final long LOGIN_PARAMETER_EXPIRE = 10 * 60 * 1000L;

	/**
	 * 请求登录前先获取salt, AES IV
	 * @return
	 */
	@GetMapping(value = "salt")
	@ApiOperation(value = "请求登录前先获取salt, AES IV")
	public Result<LoginSaltDto> salt() {
		LoginSaltDto saltDto = new LoginSaltDto();
		saltDto.setSalt(System.currentTimeMillis() + "");
		saltDto.setIv(loginIv);

		return Result.success(saltDto);
	}

	/**
	 * 登录
	 * @param body
	 * @return
	 */
	@PostMapping(value = "login")
	@ApiOperation(value = "登录")
	public Result<SystemUserDto> login(HttpServletRequest servletRequest, @RequestBody String body) throws DecoderException {
		if (StringUtils.isEmpty(body)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		JSONObject jsonObj = JSONObject.parseObject(body);
		String userName = jsonObj.getString("userName");
		String cipherPassword = jsonObj.getString("password");
		// md5(password + loginkey + time)
		String md5 = jsonObj.getString("md5");
		// 毫秒
		long time = jsonObj.getLongValue("salt");

		if (StringUtils.isEmpty(userName)) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		if (StringUtils.isEmpty(cipherPassword)) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		if (System.currentTimeMillis() > time + LOGIN_PARAMETER_EXPIRE) {
			return resultFactory.error(ResultCode.PARAMETER_EXPIRE);
		}
		String expectMd5 = Md5DigestUtils.digest(cipherPassword + loginKey + time);
		if (!expectMd5.equals(md5)) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}

		byte[] plainBytes = CryptUtils.aesDecrypt(Hex.decodeHex(cipherPassword), Base64.decodeBase64(loginKey), Base64.decodeBase64(loginIv));
		// 解密后的密码
		String password = new String(plainBytes);

		// 获取ip
		String requestIp = HttpUtils.getRequestIp(servletRequest);
		// login
		SystemUser systemUser = systemUserService.login(userName, password, requestIp);

		String token = tokenService.create(systemUser.getId());
		servletResponse.setHeader("Access-Token", token);

		SystemUserDto dto = systemAdminDtoConverter.toSystemUserDto(systemUser);
		return Result.success(dto);
	}

	/**
	 * 获取权限
	 * @return
	 */
	@GetMapping(value = "accessRight")
	@ApiOperation(value = "获取权限")
	public Result<AccessRightDto> accessRight() {
		long requestId = this.getUserId();

		// 获取角色
		List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(requestId);
		if (userRoles == null || userRoles.isEmpty()) {
			return Result.success(new AccessRightDto());
		}

		Set<String> roles = new HashSet<>();
		// 多个角色的权限集合，可能有重复的
		List<SystemRoleRight> allRights = new ArrayList<SystemRoleRight>();
		for (SystemUserRole userRole : userRoles) {
			SystemRole systemRole = systemRoleService.getSystemRole(userRole.getRoleId());
			roles.add(systemRole.getRoleName());
			List<SystemRoleRight> roleRights = systemRoleRightService.findByRoleId(userRole.getRoleId());
			if (roleRights == null || roleRights.isEmpty()) {
				continue;
			}

			allRights.addAll(roleRights);
		}

		List<SystemMenuDto> systemMenuDto = systemAdminDtoConverter.toMenuDtos(allRights);

		AccessRightDto dto = new AccessRightDto();
		dto.setRoles(roles);
		dto.setSystemMenuDto(systemMenuDto);

		return Result.success(dto);
	}

	/**
	 * 添加管理后台用户的接口
	 * @param request
	 * @return
	 */
	@PostMapping(value = "add")
	@RequiresPermissions("systemUser:create")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "添加管理后台用户")
	public Result<SystemUserDto> add(@RequestBody SystemUserRequest request) {
		// 参数检查
		this.checkParameter(request);
		
		SystemUser systemUser = systemUserService.findByUserName(request.getUserName());
		if (null != systemUser) {
			return resultFactory.error(ResultCode.USER_NAME_EXISTS);
		}
		
		long requestId = this.getUserId();
		systemUser = systemUserService.add(requestId, request);
		SystemUserDto dto = systemAdminDtoConverter.toSystemUserDto(systemUser);

		return Result.success(dto);
	}

	/**
	 * 参数检查
	 * @param request
	 */
	private void checkParameter(SystemUserRequest request) {
		if (request == null) {
			throw new BusinessException(ResultCode.PARAM_EMPTY);
		}
		// 检查用户名
		if (StringUtils.isEmpty(request.getUserName())) {
			throw new BusinessException(ResultCode.USERNAME_MIN_LENGTH);
		}
		// 检查密码
		if (StringUtils.isEmpty(request.getPassword())) {
			throw new BusinessException(ResultCode.PASSWORD_MIN_LENGTH);
		}
		// 检查昵称
		if (StringUtils.isEmpty(request.getName())) {
			throw new BusinessException(ResultCode.NICKNAME_MIN_LENGTH);
		}
		if (request.getStatus() != 0 && request.getStatus() != 1) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
	}
	
	/**
	 * 删除管理后台用户
	 * @param userId
	 * @return
	 */
	@PostMapping(value = "delete/{userId}")
	@RequiresPermissions("systemUser:delete")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "删除管理后台用户")
	public Result<String> delete(@PathVariable String userId) {
		if (StringUtils.isEmpty(userId)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}

		long longUserId = this.stringToLong(userId);
		SystemUser systemUser = systemUserService.getSystemUser(longUserId);
		if (systemUser == null) {
			return resultFactory.error(ResultCode.USER_NOT_EXISTS);
		}
		if (systemUser.isSystemUser()) {
			return resultFactory.error(ResultCode.SYSTEM_USER);
		}

		// 删除角色与用户的关系
		systemUserRoleService.deleteByUserId(longUserId);
		systemUserService.delete(longUserId);

		return Result.success(userId);
	}

	/**
	 * 更新管理后台用户信息接口
	 * @param userId
	 * @param body
	 * @return
	 */
	@PostMapping(value = "update/{userId}")
	@RequiresPermissions("systemUser:update")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "更新管理后台用户信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "name", value = "姓名", type = "string"),
			@ApiImplicitParam(name = "phone", value = "新密码", type = "string"),
			@ApiImplicitParam(name = "email", value = "email", type = "string"),
			@ApiImplicitParam(name = "status", value = "状态", type = "integer")
	})
	public Result<SystemUserDto> update(@PathVariable String userId, @RequestBody String body) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(body)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		
		long longUserId = this.stringToLong(userId);
		JSONObject jsonObj = JSONObject.parseObject(body);
		String name = jsonObj.getString("name");
		String phone = jsonObj.getString("phone");
		String email = jsonObj.getString("email");
		Integer status = jsonObj.getInteger("status");
		
		if (status == null || (status != 0 && status != 1)) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		if (StringUtils.isEmpty(name)) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		
		SystemUser systemUser = systemUserService.getSystemUser(longUserId);
		if (systemUser == null) {
			return resultFactory.error(ResultCode.USER_NOT_EXISTS);
		}
		if (systemUser.isSystemUser()) {
			return resultFactory.error(ResultCode.SYSTEM_USER);
		}
		systemUser.setName(name);
		systemUser.setPhone(phone);
		systemUser.setEmail(email);
		systemUser.setStatus(status);

		long requestId = this.getUserId();
		systemUser = systemUserService.update(requestId, systemUser);
		SystemUserDto dto = systemAdminDtoConverter.toSystemUserDto(systemUser);

		return Result.success(dto);
	}
	
	/**
	 * 修改管理后台用户密码接口，只能修改自己的密码
	 * @param request
	 * @return
	 */
	@PostMapping(value = "changePassword")
	@ApiOperation(value = "修改自己的密码")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "oldPassword", value = "旧密码", type = "string"),
			@ApiImplicitParam(name = "newPassword", value = "新密码", type = "string")
	})
	public Result<Void> changePassword(@RequestBody JSONObject request) throws DecoderException {
		String oldPassword = request.getString("oldPassword");
		String newPassword = request.getString("newPassword");

		long requestId = this.getUserId();
		if (requestId <= 0) {
			return resultFactory.error(ResultCode.TOKEN_EXPIRE);
		}
		if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(oldPassword)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		if (oldPassword.equals(newPassword)) {
			return resultFactory.error(ResultCode.NEWPWD_SAMEAS_OLD);
		}

		byte[] oldPwdBytes = CryptUtils.aesDecrypt(Hex.decodeHex(oldPassword), Base64.decodeBase64(loginKey), Base64.decodeBase64(loginIv));
		byte[] newPwdBytes = CryptUtils.aesDecrypt(Hex.decodeHex(newPassword), Base64.decodeBase64(loginKey), Base64.decodeBase64(loginIv));
		// 解密后的密码
		oldPassword = new String(oldPwdBytes);
		newPassword = new String(newPwdBytes);
		
		systemUserService.changePassword(requestId, oldPassword, newPassword);
		
		return Result.success(null);
	}
	
	/**
	 * 重置他人密码接口
	 * @param userId
	 * @param request
	 * @return
	 */
	@PostMapping(value = "resetpassword/{userId}")
	@RequiresPermissions("systemUser:update")
	@ApiOperation(value = "重置他人密码接口")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "newPassword", value = "新密码", type = "string")
	})
	public Result<Void> resetPassword(@PathVariable String userId, @RequestBody JSONObject request) {
		String newPassword = request.getString("newPassword");
		
		long longUserId = this.stringToLong(userId);
		long requestId = this.getUserId();
		if (requestId <= 0) {
			return resultFactory.error(ResultCode.TOKEN_EXPIRE);
		}
		if (requestId == longUserId) {
			return resultFactory.error(ResultCode.CANNOT_RESET_PWD);
		}
		
		if (StringUtils.isEmpty(newPassword)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		
		// 重置密码
		systemUserService.resetPassword(longUserId, newPassword);
		
		return Result.success(null);
	}

	/**
	 * 分页查找用户列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping(value = "userList")
	@RequiresPermissions("systemUser:query")
	@ApiOperation(value = "分页查找用户列表")
	public Result<PageDto<SystemUserDto>> userList(@RequestParam int pageNo, @RequestParam int pageSize) {
		if (pageSize >= this.SMALL_LIST_SIZE) {
			return resultFactory.error(ResultCode.PARAM_ERROR);
		}
		
		Page<SystemUser> pageResult = systemUserService.userList(pageNo, pageSize);
		List<SystemUserDto> dtos = systemAdminDtoConverter.toSystemUserDtos(pageResult.getContent());

		PageDto<SystemUserDto> pageDto = new PageDto<SystemUserDto>();
		pageDto.setContents(dtos);
		this.setPageInfo(pageDto, pageResult);

		return Result.success(pageDto);
	}

	/**
	 * 根据id查询管理后台用户
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "getuser/{userId}")
	@RequiresPermissions("systemUser:query")
	@ApiOperation(value = "暂时不用接")
	public Result<SystemUserDto> getUser(@PathVariable String userId) {
		if (StringUtils.isEmpty(userId)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}

		long longUserId = this.stringToLong(userId);
		SystemUser systemUser = systemUserService.getSystemUser(longUserId);
		if (systemUser == null) {
			return resultFactory.error(ResultCode.USER_NOT_EXISTS);
		}

		SystemUserDto dto = systemAdminDtoConverter.toSystemUserDto(systemUser);

		return Result.success(dto);
	}

	/**
	 * 搜索用户
	 * @param userName
	 * @return
	 */
	@GetMapping(value = "searchUser")
	@RequiresPermissions("systemUser:query")
	public Result<List<SimpleSystemUserDto>> searchUser(@RequestParam String userName) {
		if (StringUtils.isEmpty(userName)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}

		List<SystemUser> systemUsers = systemUserService.findByUserNameLike(userName);
		List<SimpleSystemUserDto> dtos = systemAdminDtoConverter.toSimpleSystemUserDtos(systemUsers);

		return Result.success(dtos);
	}

	/**
	 * 管理端界面注销
	 * @return
	 */
	@GetMapping(value = "logout")
	@ApiOperation(value = "退出、注销")
	public Result<Void> logout() {
		long userId = this.getUserId();
		localCacheService.remove(CacheKeys.USER_ACCESS_RIGHTS + userId);

//		Subject currentUser = SecurityUtils.getSubject();
//		if (SecurityUtils.getSubject().getSession() != null) {
//			currentUser.logout();
//		}

		return Result.success(null);
	}



	/*
    发送短信  SMS: Short Message Service
     */
	@PostMapping(value = "sendSms")
	@ApiOperation(value = "发送短信")
	protected  Result<Void> sendSms(@RequestBody String phone)  {
		if (StringUtils.isEmpty(phone)) {
			return resultFactory.error(ResultCode.PARAM_EMPTY);
		}
		systemUserService.getUserPhone(phone);
		return Result.success(null);


	}

}
