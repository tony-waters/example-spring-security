package com.example.model.security;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="ROLE")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long entityId;

	@Version
	@Column(name = "VERSION")
	private Integer version;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToMany
	@JoinTable(
			name = "ROLE_PERMISSION", 
			joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}, 
			inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID")}
	)
	private Collection<Permission> permissions = new HashSet<Permission>();
	
	public Collection<Permission> getPermissions() {
		return permissions;
	}

}
