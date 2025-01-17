package com.chavindu.c_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(CChatApplication.class, args);
	}

}
