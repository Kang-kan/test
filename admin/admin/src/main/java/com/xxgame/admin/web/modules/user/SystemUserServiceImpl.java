package com.xxgame.admin.web.modules.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.modules.user.controller.model.SystemUserRequest;
import com.xxgame.admin.web.util.CryptUtils;
import com.xxgame.admin.web.util.JedisUtils;
import com.xxgame.admin.web.util.PBKDF2Utils;
import com.xxgame.admin.web.util.ShaDigestUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.xxgame.admin.web.modules.commons.IdWorkerService;
import com.xxgame.admin.web.modules.user.entity.SystemUser;
import com.xxgame.admin.web.modules.user.entity.SystemUserLog;
import com.xxgame.admin.web.modules.user.repository.SystemUserLogRepository;
import com.xxgame.admin.web.modules.user.repository.SystemUserRepository;
import redis.clients.jedis.Jedis;

/**
 * 管理后台用户
 * 
 * @author gil
 *
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

	@Autowired
	private SystemUserRepository systemUserRepository;
	@Autowired
	private SystemUserLogRepository systemUserLogRepository;
	@Autowired
	private IdWorkerService idWorkerService;
	@Autowired
	private UserRuleService userRuleService;
	@Autowired
	private byte[] serverKeyBytes;

	@Override
	public SystemUser login(String userName, String password, String loginIp) {
		SystemUser systemUser = systemUserRepository.findByUserName(userName);
		if (systemUser == null) {
			throw new BusinessException(ResultCode.USER_NAME_PWD_ERROR);
		}
		if (systemUser.getStatus() == 0) {
			throw new BusinessException(ResultCode.USER_DISABLE);
		}

		byte[] shaPassword = ShaDigestUtils.sha512(password.getBytes());
		byte[] hashPassword = PBKDF2Utils.hashPassword(Base64.encodeBase64String(shaPassword), systemUser.getSalt());
		byte[] ivBytes = new byte[16];
		System.arraycopy(hashPassword, 0, ivBytes, 0, 16);

		byte[] aesPwdBytes = CryptUtils.aesEncrypt(hashPassword, serverKeyBytes, ivBytes);
		if (!Arrays.equals(aesPwdBytes, systemUser.getPassword())) {
			throw new BusinessException(ResultCode.USER_NAME_PWD_ERROR);
		}

		// 日志
		SystemUserLog systemUserLog = new SystemUserLog();
		systemUserLog.setUserId(systemUser.getId());
		systemUserLog.setLoginIp(loginIp);
		systemUserLog.setLoginTime(System.currentTimeMillis());
		systemUserLog.setEvents("login");
		systemUserLogRepository.save(systemUserLog);
		
		return systemUser;
	}
	
    /**
     * 获取SystemUser
     * @param id
     * @return
     */
	@Override
    public SystemUser getSystemUser(long id) {
    	return systemUserRepository.findById(id).get();
    }

	@Override
	public List<SystemUser> findUsers(Collection<Long> ids) {
		return systemUserRepository.findAllById(ids);
	}

	/**
	 * 添加EsSystemUser
	 * @param requestId
	 * @param request
	 * @return
	 */
	@Override
	public SystemUser add(long requestId, SystemUserRequest request) {
		// 参数检查
		this.checkParameter(request);
		
		SystemUser systemUser = new SystemUser();
        systemUser.setId(idWorkerService.nextCommonId());
        systemUser.setCreateTime(System.currentTimeMillis());
        systemUser.setOperator(requestId);
        systemUser.setUserName(request.getUserName());
        systemUser.setName(request.getName());
        systemUser.setStatus(request.getStatus());
        systemUser.setPhone(request.getPhone() == null ? "" : request.getPhone());
        systemUser.setEmail(request.getEmail() == null ? "" : request.getEmail());
        systemUser.setStatus(request.getStatus());
        systemUser.setUpdateTime(System.currentTimeMillis());

		// 设置密码安全
		cryptPassword(systemUser, request.getPassword());

		return systemUserRepository.save(systemUser);
	}
	
	/**
	 * 参数检查
	 * @param request
	 */
	private void checkParameter(SystemUserRequest request) {
		// 检查用户名
		if (request.getUserName().length() < userRuleService.minUserNameLength()) {
			throw new BusinessException(ResultCode.USERNAME_MIN_LENGTH);
		}
		if (request.getUserName().length() >= userRuleService.maxUserNameLength()) {
			throw new BusinessException(ResultCode.USERNAME_MAX_LENGTH);
		}
		// 检查密码
		if (request.getPassword().length() < userRuleService.minPasswordLength()) {
			throw new BusinessException(ResultCode.PASSWORD_MIN_LENGTH);
		}
		if (request.getPassword().length() >= userRuleService.maxPasswordLength()) {
			throw new BusinessException(ResultCode.PASSWORD_MAX_LENGTH);
		}
		if (request.getPassword().equals(request.getUserName())) {
			throw new BusinessException(ResultCode.PASSWORD_UNUSEFUL);
		}
		if (!userRuleService.passwordUseful(request.getPassword())) {
			throw new BusinessException(ResultCode.PASSWORD_UNUSEFUL);
		}
		// 检查昵称
		if (request.getName().length() < userRuleService.minNameLength()) {
			throw new BusinessException(ResultCode.NICKNAME_MIN_LENGTH);
		}
		if (request.getName().length() >= userRuleService.maxNameLength()) {
			throw new BusinessException(ResultCode.NICKNAME_MAX_LENGTH);
		}
		
		if (StringUtils.isNotEmpty(request.getPhone()) && request.getPhone().length() >= 32) {
			throw new BusinessException(ResultCode.PARAM_MAX_LENGTH);
		}
		if (StringUtils.isNotEmpty(request.getEmail()) && request.getEmail().length() >= 32) {
			throw new BusinessException(ResultCode.PARAM_MAX_LENGTH);
		}
	}
	
	/**
	 * 更新SystemUser，只更新附加信息。
	 * @param requestId
	 * @param systemUser
	 * @return
	 */
	@Override
	public SystemUser update(long requestId, SystemUser systemUser) {
		// 参数检查
		if (systemUser.getName().length() >= userRuleService.maxNameLength()) {
			throw new BusinessException(ResultCode.NICKNAME_MAX_LENGTH);
		}
		if (StringUtils.isNotEmpty(systemUser.getPhone()) && systemUser.getPhone().length() >= 32) {
			throw new BusinessException(ResultCode.PARAM_MAX_LENGTH);
		}
		if (StringUtils.isNotEmpty(systemUser.getEmail()) && systemUser.getEmail().length() >= 32) {
			throw new BusinessException(ResultCode.PARAM_MAX_LENGTH);
		}
		
        systemUser.setPhone(systemUser.getPhone() == null ? "" : systemUser.getPhone());
        systemUser.setEmail(systemUser.getEmail() == null ? "" : systemUser.getEmail());
		systemUser.setOperator(requestId);
        systemUser.setUpdateTime(System.currentTimeMillis());
        
        return systemUserRepository.save(systemUser);
	}
	
	/**
	 * 修改密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 */
	@Override
	public void changePassword(long userId, String oldPassword, String newPassword) {
		SystemUser systemUser = systemUserRepository.findById(userId).get();
		if (null == systemUser) {
			throw new BusinessException(ResultCode.USER_NOT_EXISTS);
		}

		// 检查旧密码是否正确
		byte[] shaPassword = ShaDigestUtils.sha512(oldPassword.getBytes());
		byte[] hashPassword = PBKDF2Utils.hashPassword(Base64.encodeBase64String(shaPassword), systemUser.getSalt());
		byte[] ivBytes = new byte[16];
		System.arraycopy(hashPassword, 0, ivBytes, 0, 16);

		byte[] aesPwdBytes = CryptUtils.aesEncrypt(hashPassword, serverKeyBytes, ivBytes);
		if (!Arrays.equals(aesPwdBytes, systemUser.getPassword())) {
			throw new BusinessException(ResultCode.USER_NAME_PWD_ERROR);
		}
		
		// 设置密码
		setPassword(userId, newPassword, systemUser);
	}

	/**
	 * 设置密码
	 * @param userId
	 * @param newPassword
	 * @param systemUser
	 */
	private void setPassword(long userId, String newPassword, SystemUser systemUser) {
		// 参数检查
		if (newPassword.length() < userRuleService.minPasswordLength()) {
			throw new BusinessException(ResultCode.PASSWORD_MIN_LENGTH);
		}
		if (newPassword.length() >= userRuleService.maxPasswordLength()) {
			throw new BusinessException(ResultCode.PASSWORD_MAX_LENGTH);
		}
		if (!userRuleService.passwordUseful(newPassword)) {
			throw new BusinessException(ResultCode.PASSWORD_UNUSEFUL);
		}
		
		if (systemUser.getStatus() == 0) {
			throw new BusinessException(ResultCode.USER_DISABLE);
		}
		if (newPassword.equals(systemUser.getUserName())) {
			throw new BusinessException(ResultCode.PASSWORD_UNUSEFUL);
		}

		// 设置密码安全
		cryptPassword(systemUser, newPassword);

		systemUser.setOperator(userId);
		systemUserRepository.save(systemUser);
	}

	/**
	 * 设置密码安全
	 * @param systemUser
	 * @param password
	 */
	private void cryptPassword(SystemUser systemUser, String password) {
		byte[] shaPassword = ShaDigestUtils.sha512(password.getBytes());
		byte[] salt = PBKDF2Utils.createKey(512);
		byte[] hashPassword = PBKDF2Utils.hashPassword(Base64.encodeBase64String(shaPassword), salt);
		byte[] ivBytes = new byte[16];
		System.arraycopy(hashPassword, 0, ivBytes, 0, 16);
		byte[] aesPwdBytes = CryptUtils.aesEncrypt(hashPassword, serverKeyBytes, ivBytes);

		systemUser.setSalt(salt);
		systemUser.setPassword(aesPwdBytes);
		systemUser.setUpdateTime(System.currentTimeMillis());
	}

	/**
	 * 重置密码
	 * @param userId
	 * @param newPassword
	 */
	@Override
	public void resetPassword(long userId, String newPassword) {
		SystemUser systemUser = systemUserRepository.findById(userId).get();
		if (null == systemUser) {
			throw new BusinessException(ResultCode.USER_NOT_EXISTS);
		}
		
		// 设置密码
		setPassword(userId, newPassword, systemUser);
	}
	
	/**
	 * 根据用户查找
	 * @param userName
	 * @return
	 */
	@Override
	public SystemUser findByUserName(String userName) {
		return systemUserRepository.findByUserName(userName);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@Override
	public void delete(long id) {
		systemUserRepository.deleteById(id);
	}
	
	/**
	 * 分页查找用户列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<SystemUser> userList(int pageNo, int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		return systemUserRepository.findAll(pageRequest);
	}

	@Override
	public List<SystemUser> findByUserNameLike(String userName) {
		return systemUserRepository.findByUserNameLike(userName);
	}

	@Override
	public void getUserPhone(String phone) {
		//1.获取手机号
		String phones = systemUserRepository.getUserPhone(phone);
		//2.随机生成验证码，6位验证码
		String authCode = RandomStringUtils.randomNumeric(6);
		System.out.println("验证码是：" + authCode);
		//3.发送短信
		Result result = sendSms(phones, authCode);
		//4.如果发送成功，将验证码保存到会话域中
		if (result.getCode()==200) {
			//键：smsCode_15912345678  值：890890
			//request.getSession().setAttribute("smsCode_" + telephone, authCode);

			//放在redis中
			Jedis jedis = JedisUtils.getJedis();
			//将验证码放在redis中，过期时间5分钟
			jedis.setex("smsCode_" + phones, 300, authCode);
			//关闭连接
			jedis.close();
		}
	}
	/**
	 * 发送短信服务
	 * @param telephone 电话号码
	 * @param authCode 验证码
	 * @return 返回结果
	 */
	public Result sendSms(String telephone, String authCode) {
		//调用工具类发送短信
		//String code = SmsUtils.send(telephone, "黑马旅游", "SMS_195723031", authCode);

		//以后就不需要再发送了
		String code = "200";
		//判断code是否是OK，表示发送成功
		if (code=="200") {
			return Result.valueOf(200,"验证码发送成功");
		}
		else {
			return  Result.valueOf(201,"验证码发送失败");
		}
	}

}
