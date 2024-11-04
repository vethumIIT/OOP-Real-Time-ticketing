package com.project.realtime_ticketing_system;

import com.project.realtime_ticketing_system.config.SqliteSetup;
import jakarta.servlet.annotation.HttpConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {
		"com.project.realtime_ticketing_system.controller",
		"com.project.realtime_ticketing_system.services",
		"com.project.realtime_ticketing_system.repositories"
		})
public class RealtimeTicketingSystemApplication {

	public static void main(String[] args) {
		SqliteSetup sqliteSetup = new SqliteSetup();
		sqliteSetup.setup();

		SpringApplication.run(RealtimeTicketingSystemApplication.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "Index Page!";
	}

	@RequestMapping("/hello")
	public String hello(){
		return "Hello World!";
	}

}
