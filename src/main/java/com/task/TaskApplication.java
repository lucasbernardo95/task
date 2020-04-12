package com.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TaskApplication extends SpringBootServletInitializer {//extends for add context web

	//retorna um objeto de configuração web
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TaskApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

}
