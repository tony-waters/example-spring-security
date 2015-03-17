package com.example.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long entityId;

	@Version
	@Column(name = "VERSION")
	private Integer version;

	@Embedded
	private Username username;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;
	
	public Member() {
	}
	
	public Member(Username username, String firstName, String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUsername() {
		return username.getValue();
	}

	@Override
	public String toString() {
		return "Member [entityId=" + entityId + ", version=" + version
				+ ", username=" + username + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
