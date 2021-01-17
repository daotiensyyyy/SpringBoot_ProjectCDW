package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.CartItem;
import org.springbootapp.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
	UserEntity save(UserEntity user);

	List<UserEntity> getAll();

	void delete(Long id);

	void update(Long id, UserEntity user);

	void updatePassword(String password, Long id);

	Optional<UserEntity> findUserByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	void addItemToCart(Long userID, CartItem item);

	Optional<UserEntity> findUserByResetToken(String resetToken);
}
