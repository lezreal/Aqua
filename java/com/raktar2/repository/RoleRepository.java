package com.raktar2.repository;

import org.springframework.data.repository.CrudRepository;

import com.raktar2.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String rolename);
	
}
