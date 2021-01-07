package org.springbootapp.repository;

import org.springbootapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
