package com.manageuniversity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.manageuniversity")
@EnableCaching
@EnableAsync
public class ManageUniversityApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ManageUniversityApplication.class, args);
	}

}
