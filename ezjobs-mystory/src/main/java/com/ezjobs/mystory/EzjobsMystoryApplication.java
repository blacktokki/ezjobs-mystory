package com.ezjobs.mystory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class EzjobsMystoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzjobsMystoryApplication.class, args);
	}

}
