package com.raktar2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.raktar2","security","controller"})
public class Raktar2Application {

	public static void main(String[] args) {
		SpringApplication.run(Raktar2Application.class, args);
	}

}

