package com.nubes.ipl2020.auth.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class User {

	@Id
	@JsonIgnore
	private String id;
	private String username;
	private String password;
	private String email;
	@JsonIgnore
	private List<Role> roles=new ArrayList<Role>();
	
	public List<Role> addRole(Role role) {
		this.roles.add(role);
		return this.roles;
	}
	
	
}