package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.common.ERole;
import org.springbootapp.entity.RoleEntity;

public interface IRoleService {
	Optional<RoleEntity> findRoleByName(ERole roleName);
}
