package org.springbootapp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springbootapp.dto.SignupRequest;
import org.springbootapp.entity.UserEntity;

public interface IUserService extends UserDetailsService {
	UserEntity save(UserEntity user);

	List<UserEntity> getAll();

	void delete(Long id);

	void update(Long id, UserEntity user);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
