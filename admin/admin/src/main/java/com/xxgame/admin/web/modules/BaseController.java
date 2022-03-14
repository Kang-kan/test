package com.xxgame.admin.web.modules;

import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.exception.ServerRightException;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.model.SessionKeys;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.result.ResultFactory;
import com.xxgame.admin.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 基础的Controller 类
 * @author gil
 *
 */
public abstract class BaseController {
	
	/**
	 * 返回结果工厂类
	 */
	@Autowired
	protected ResultFactory resultFactory;
	
	/**
	 * HttpServletRequest
	 */
	@Autowired
	protected HttpServletRequest servletRequest;

	/**
	 * HttpServletResponse
	 */
	@Autowired
	protected HttpServletResponse servletResponse;

	@Autowired
	private GameServerService gameServerService;

	/**
	 * 一个较大的集合长度
	 */
	protected final int SMALL_LIST_SIZE = 99;
	
	/**
	 * 一个很大的集合长度
	 */
	protected final int LARGE_LIST_SIZE = 999;
	
	/**
	 * logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 将字符串类型转为　long类型
	 * @param id
	 * @return
	 */
	public long stringToLong(String id) {
		try {
			return Long.valueOf(id).longValue();
		} catch (Exception e) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
	}

	/**
	 * 获取用户id
	 * @return
	 */
	protected long getUserId() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		Long value = (Long) attributes.getAttribute(SessionKeys.SYSTEM_USER_ID, RequestAttributes.SCOPE_REQUEST);
		return value != null ? value : 0;
	}

	/**
	 * 设置分页信息
	 * @param pageDto
	 * @param pageResult
	 */
	protected void setPageInfo(PageDto<?> pageDto, Page<?> pageResult) {
		pageDto.setPageSize(pageResult.getPageable().getPageSize());
		pageDto.setPageNo(pageResult.getPageable().getPageNumber());
		pageDto.setTotalPage(pageResult.getTotalPages());
		pageDto.setTotalCount(pageResult.getTotalElements());
	}

	/**
	 * 将分页信息转为游戏服需要的参数
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	protected int[] toQueryArgs(int pageNo, int pageSize) {
		int startIndex = pageNo * pageSize;
		int queryCount = pageSize;

		return new int[]{ startIndex, queryCount };
	}

	/**
	 * 将数值时间转换成Date
	 * @param dayOfNumber 如：20191120
	 * @return
	 */
	protected Date dayToDate(int dayOfNumber) {
		return DateUtils.numberToDate(dayOfNumber, DateUtils.PATTERN_YYYYMMDD);
	}

	/**
	 * 检查是否有该服务器的权限
	 * @param serverIds
	 */
	protected void checkServerRight(List<Integer> serverIds) {
		// 检查是否有权限
		long userId = this.getUserId();
		for (int serverId : serverIds) {
			boolean right = gameServerService.isHaveThisServer(userId, serverId);
			if (!right) {
				throw new ServerRightException();
			}
		}
	}

}
