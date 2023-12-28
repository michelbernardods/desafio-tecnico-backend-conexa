package com.conexa.saude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SaudeConexaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaudeConexaApplication.class, args);
		System.out.println("OK");
	}
}
