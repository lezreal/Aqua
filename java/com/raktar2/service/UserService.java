package com.raktar2.service;



import com.raktar2.entities.Rendeles;
import com.raktar2.entities.User;


public interface UserService {


	
	public void addUserToDb(User user);
	
	User findByEmail(String email);
	
	public User findByActivator(String code);
	
	public void addRendelesToDb(Rendeles rend);
	
}
