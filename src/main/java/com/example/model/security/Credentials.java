package com.example.model.security;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="CREDENTIALS")
public class Credentials {

	@Id
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;
	
	@ManyToMany
	@JoinTable(
			name = "MEMBER_ROLE", 
			joinColumns = {@JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")}, 
			inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
	)
	private Collection<Role> roles = new HashSet<Role>();
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Boolean isEnabled() {
		return enabled;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}

}
