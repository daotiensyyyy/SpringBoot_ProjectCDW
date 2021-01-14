package org.springbootapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springbootapp.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity>findByUsername(String username);
	
	UserEntity findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}
