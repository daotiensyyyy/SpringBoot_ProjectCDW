package org.springbootapp;

import java.util.Optional;

import org.springbootapp.entity.UserEntity;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectCdwApplication implements CommandLineRunner{

	@Autowired
	IUserService service;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectCdwApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<UserEntity> findUserByEmail = service.findUserByEmail("sydao1579@gmail.com");
		System.out.println(findUserByEmail.get());
		
		String resetToken = "f41688b1-328d-4858-8d77-7fc36ac6282c";
		Optional<UserEntity> findUserByResetToken = service.findUserByResetToken(resetToken);
		System.out.println(findUserByEmail.get());
		
	}
	
}
