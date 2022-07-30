package org.barclays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApp {
	
	public static ApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(StoreApp.class, args);
	}
}