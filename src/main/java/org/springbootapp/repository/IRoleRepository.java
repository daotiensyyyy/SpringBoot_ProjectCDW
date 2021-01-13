package org.springbootapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springbootapp.common.ERole;
import org.springbootapp.entity.RoleEntity;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long>{
	Optional<RoleEntity>findByName(ERole role);
}
