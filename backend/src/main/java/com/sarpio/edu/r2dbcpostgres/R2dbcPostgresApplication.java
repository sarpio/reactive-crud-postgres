package com.sarpio.edu.r2dbcpostgres;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class R2dbcPostgresApplication {

	public static void main(String[] args) {
		SpringApplication.run(R2dbcPostgresApplication.class, args);
	}


}
