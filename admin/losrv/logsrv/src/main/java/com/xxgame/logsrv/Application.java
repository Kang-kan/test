package com.xxgame.logsrv;

import com.xxgame.logsrv.module.mq.ConsumerService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序入口
 *
 * @author gil
 *
 */
@ComponentScan(value = { "com.xxgame.logsrv" })
@SpringBootApplication
public class Application {

	/**
	 * main
	 *
	 * @param args
	 */
	public static void main(String[] args) throws MQClientException {
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
		ConsumerService consumerService = applicationContext.getBean(ConsumerService.class);
		consumerService.start();
	}

}
