package com.gft.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication
public class DesafioMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioMvcApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("Gft@1234"));
	
	}

}
