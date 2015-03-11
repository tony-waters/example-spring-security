package com.example.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="PERMISSION")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long entityId;

	@Version
	@Column(name = "VERSION")
	private Integer version;
	
	@Column(name="NAME")
	private String name;

	public String getName() {
		return name;
	}
	
}
