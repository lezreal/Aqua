package com.raktar2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar2.entities.Rendeles;
import com.raktar2.entities.User;

public interface RendelesRepository extends CrudRepository<Rendeles, Long> {
	
	
	List<Rendeles> findAllByUser(User user);
}
