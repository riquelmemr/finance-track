package com.riquelmemr.financetrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinanceTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceTrackApplication.class, args);
	}

}
