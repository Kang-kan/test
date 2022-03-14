package com.xxgame.admin.web.modules.commons;

import javax.annotation.PostConstruct;

import com.xxgame.admin.web.util.CommonIdWorker;
import com.xxgame.admin.web.util.ShortIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 业务id生成器
 * @author gil
 *
 */
@Service
public class IdWorkerService {

	/**
	 * serverId
	 */
	@Value("${social.serverId}")
	private int serverId;
	
	/**
	 * datacenterId
	 */
	@Value("${social.datacenterId}")
	private int datacenterId;

	/**
	 * 通用的id生成器
	 */
	private CommonIdWorker commonIdWorker;
	
	/**
	 * 用户id生成器
	 */
	private ShortIdWorker shortIdWorker;
	
	@PostConstruct
	private void init() {
		commonIdWorker = new CommonIdWorker(serverId, datacenterId);
		shortIdWorker = new ShortIdWorker(serverId);
	}
	
	/**
	 * 通用的id
	 * @return
	 */
	public long nextCommonId() {
		return commonIdWorker.nextId();
	}
	
	/**
	 * 较短的id
	 * @return
	 */
	public long nextShortId() {
		return shortIdWorker.nextId();
	}
	
}
