package com.miniproject.TruePrep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.miniproject.TruePrep.Repository") // Add this line to enable repositories in the specified package
public class TruePrepApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruePrepApplication.class, args);
	}
}
