package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.RoleEntity;
import org.springbootapp.repository.IRoleRepository;
import org.springbootapp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService{

	@Autowired
	private IRoleRepository repository;
	
	@Override
	public Optional<RoleEntity> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public RoleEntity findRoleByName(String roleName) {
		
		return repository.findRoleByName(roleName);
	}

	@Override
	public List<RoleEntity> findAll() {
		return repository.findAll();
	}

	@Override
	public void save(RoleEntity role) {
		repository.save(role);
	}

}
