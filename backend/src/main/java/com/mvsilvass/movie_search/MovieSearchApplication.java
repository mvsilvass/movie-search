package com.mvsilvass.movie_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieSearchApplication.class, args);
	}

}
