package com.xxgame.admin.web;


import com.xxgame.admin.web.LabMQ.RocketMqConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序入口
 * @author gil
 *
 */
@SpringBootApplication
@EnableCircuitBreaker
@ComponentScan(value = { "com.xxgame.admin.web" })
public class AdminApplication {
	
	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
		RocketMqConsumer.getInstance().init();
	}
	
}

