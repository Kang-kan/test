package com.xxgame.logsrv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 公共配置
 * 
 * @author gil
 *
 */
@Configuration
public class CommonConfiguration {

	/**
	 * ThreadPoolTaskScheduler
	 * @return
	 */
	@Bean(name = "threadPoolTaskScheduler")
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(5);
		taskScheduler.initialize();

		return taskScheduler;
	}

	/**
	 * ScheduledThreadPoolExecutor
	 * @param threadPoolTaskScheduler
	 * @return
	 */
	@Bean(name = "scheduledThreadPoolExecutor")
	public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		return threadPoolTaskScheduler.getScheduledThreadPoolExecutor();
	}

}
