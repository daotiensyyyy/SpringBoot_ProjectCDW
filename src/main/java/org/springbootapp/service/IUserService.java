package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
	UserEntity save(UserEntity user);

	List<UserEntity> getAll();

	void delete(Long id);

	void update(Long id, UserEntity user);
	
	UserEntity findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
