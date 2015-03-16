package com.example.model.security;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.example.model.Username;

@Entity
@Table(name="CREDENTIALS")
public class Credentials {

	@EmbeddedId
//	@AttributeOverride(name = "value", column = @Column(name = "USERNAME"))
	private Username username;
	
//	@Column(name = "PASSWORD")
	@Embedded
	private Password password;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;
	
	@ManyToMany
	@JoinTable(
			name = "CREDENTIALS_ROLE", 
			joinColumns = {@JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")}, 
			inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
	)
	private Collection<Role> roles = new HashSet<Role>();
	
	public String getUsername() {
		return username.getValue();
	}

	public String getPassword() {
		return password.getValue();
	}

	public Boolean isEnabled() {
		return enabled;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}

}
