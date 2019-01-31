package com.raktar2.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.raktar2.entities.Role;

@Entity
public class User {

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	Long id;
	
	public User() {}
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	String name;
	
	String password;
	String email;
	String activator;
	String city;
	String street;
	String phone;
	String comment;
	
	public String getComment() {
		return comment;
	}




	public void setComment(String comment) {
		this.comment = comment;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public String getStreet() {
		return street;
	}




	public void setStreet(String street) {
		this.street = street;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}

	boolean enabled;
	
	
	
	
	
	
	
	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getActivator() {
		return activator;
	}




	public void setActivator(String activator) {
		this.activator = activator;
	}




	public boolean isEnabled() {
		return enabled;
	}




	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}




	public List<Rendeles> getRendelesek() {
		return rendelesek;
	}




	public void setRendelesek(List<Rendeles> rendelesek) {
		this.rendelesek = rendelesek;
	}

	@OneToMany(mappedBy="user")
	List<Rendeles> rendelesek;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
			name="users_roles",
			joinColumns= {@JoinColumn(name="user_id")},
			inverseJoinColumns= {@JoinColumn(name="role_id")}
			
	)
	Set<Role> roles = new HashSet<Role>();
	
	
	
	public void addRole(String roleName) {
		if (this.roles==null || this.roles.isEmpty()) this.roles = new HashSet();
		
		this.roles.add(new Role(roleName));
	}




	public Set<Role> getRoles() {
		return roles;
	}




	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
