package com.example.ms_4_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.ms_4_2")
public class Ms42Application {

	public static void main(String[] args) {
		SpringApplication.run(Ms42Application.class, args);
	}

}
