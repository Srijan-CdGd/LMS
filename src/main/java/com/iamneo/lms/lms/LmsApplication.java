package com.iamneo.lms.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @OpenAPIDefinition(info = @Info(title = "LMS API", version = "1.0.0",
// description = "BooksMyShow API Documentation with Spring"))
public class LmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
		System.out.println("Up and running");
	}

}
