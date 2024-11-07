package com.healthyage.healthyage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HealthyageApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthyageApplication.class, args);
	}
}
