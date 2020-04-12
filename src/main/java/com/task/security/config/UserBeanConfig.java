package com.task.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.task.model.User;
import com.task.repository.UserRepository;

@Configuration
public class UserBeanConfig {
	@Autowired
	UserRepository repo;
	
	@Bean
	public void createFirstUser() {
		User user = new User("admin","admin", new BCryptPasswordEncoder().encode("admin"), null);
		User userReturn = repo.findUserByLogin(user.getLogin());
		
		if(userReturn == null)
			repo.save(user);
	}
}
