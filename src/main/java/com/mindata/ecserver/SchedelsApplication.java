package com.mindata.ecserver;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableScheduling
public class SchedelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedelsApplication.class, args);
	}
}
