package com.cib.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScraperApplication.class, args);
	}

}
