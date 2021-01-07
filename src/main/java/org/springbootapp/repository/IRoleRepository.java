package org.springbootapp.repository;

import org.springbootapp.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long>{
	RoleEntity findRoleByName(String roleName);
}
