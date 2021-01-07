package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.RoleEntity;

public interface IRoleService {
	Optional<RoleEntity> findById(Long id);
	RoleEntity findRoleByName(String roleName);
	List<RoleEntity>findAll();
	void save(RoleEntity role);
}
