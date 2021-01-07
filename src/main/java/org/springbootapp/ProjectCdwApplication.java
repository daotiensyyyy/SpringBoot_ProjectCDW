package org.springbootapp;

import org.springbootapp.entity.UserEntity;
import org.springbootapp.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectCdwApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectCdwApplication.class, args);
	}
	
//	@Autowired
//    IUserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//	
//	@Override
//    public void run(String... args) throws Exception {
//        // Khi chương trình chạy
//        // Insert vào csdl một user.
//        UserEntity user = new UserEntity();
//        user.setUsername("loda");
//        user.setPassword(passwordEncoder.encode("loda"));
//        userRepository.save(user);
//        System.out.println(user);
//    }
}
