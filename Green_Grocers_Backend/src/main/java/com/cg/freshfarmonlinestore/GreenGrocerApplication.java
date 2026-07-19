package com.cg.freshfarmonlinestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class GreenGrocerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenGrocerApplication.class, args);
	}

}
