package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * 消费者项目启动类
 * @author tom
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ConsumerStart {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerStart.class, args);
	}

}
