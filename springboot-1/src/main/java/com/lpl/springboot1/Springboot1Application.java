package com.lpl.springboot1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.lpl.springboot1.dao")
public class Springboot1Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot1Application.class, args);
	}
}
