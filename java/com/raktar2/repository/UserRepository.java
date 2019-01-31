package com.raktar2.repository;

import org.springframework.data.repository.CrudRepository;

import com.raktar2.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	

	User findByActivator(String code);
	
	User findByEmail(String email);
	
	
	
}
