package org.springbootapp.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springbootapp.common.ERole;
import org.springbootapp.entity.RoleEntity;
import org.springbootapp.repository.IRoleRepository;
import org.springbootapp.service.IRoleService;

@Service
public class RoleService implements IRoleService{
	
	@Autowired
	IRoleRepository roleRepository;

	@Override
	public Optional<RoleEntity> findRoleByName(ERole roleName) {
		return roleRepository.findByName(roleName);
	}


}
