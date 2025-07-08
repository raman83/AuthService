package com.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "com.authservice",  // your current service
	    "com.authcore"      // where RsaKeyConfig lives
	})

@EnableFeignClients(basePackages = {
	    "com.authcore.client",     // this is where ConsentClient lives
	})
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	
	
	@PostConstruct
	public void printPort() {
	    System.out.println("Active server porttttttt: " + System.getProperty("server.port"));
	}
}
