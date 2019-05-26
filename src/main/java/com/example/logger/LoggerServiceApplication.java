package com.example.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoggerServiceApplication {

    static Logger logger = LogManager.getLogger(LoggerServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LoggerServiceApplication.class, args);
	}
}
