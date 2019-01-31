package com.raktar2.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {

	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	int id;
	
	
	private String role;
	
	@ManyToMany(mappedBy="roles")
	private Set<User> users = new HashSet<User>();
	
	
	private Role() {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Role(String name) {
		this.role=name;
		
	}
}
