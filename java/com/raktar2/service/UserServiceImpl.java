package com.raktar2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raktar2.repository.RoleRepository;
import com.raktar2.entities.Role;
import com.raktar2.entities.Rendeles;
import com.raktar2.entities.User;
import com.raktar2.repository.RendelesRepository;
import com.raktar2.repository.UserRepository;
import com.raktar2.security.UserDetailsImpl;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RendelesRepository rendelesRepo;
	
	private final String USER_ROLE ="USER";
	
	public void addUserToDb(User user) {
		Role newRole = roleRepo.findByRole(USER_ROLE);
		
		if (newRole!=null) {   // ha van már "USER" a ROLE REPOban
			user.getRoles().add(newRole);
		} else {
			user.addRole(USER_ROLE);
		}
		
		userRepo.save(user);
		
	}

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	public User findByActivator(String code) {
		// TODO Auto-generated method stub
		return userRepo.findByActivator(code);
	}

	public void addRendelesToDb(Rendeles rend) {
		rendelesRepo.save(rend);
		
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		
		if (user==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserDetailsImpl(user);
	}

	
	
}
