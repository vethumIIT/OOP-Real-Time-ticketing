package com.project.realtime_ticketing_system;

import com.project.realtime_ticketing_system.config.AllowedOrigins;
import com.project.realtime_ticketing_system.config.SqliteSetup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.project.realtime_ticketing_system.controller",
		"com.project.realtime_ticketing_system.services",
		"com.project.realtime_ticketing_system.repositories"
		})
@CrossOrigin(origins = AllowedOrigins.allowedOrigins, allowCredentials = "true")
public class RealtimeTicketingSystemApplication {

	public static void main(String[] args) {
		SqliteSetup sqliteSetup = new SqliteSetup();
		sqliteSetup.setup();
		SpringApplication.run(RealtimeTicketingSystemApplication.class, args);
	}


}
