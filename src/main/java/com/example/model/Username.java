package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Username implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * A username:
	 *    - must start with a letter
	 *    - must be between 4 and 15 characters long
	 *    - must only include letters, numbers, or underscore
	 */
	private static final String USERNAME_EXPRESSION = "^[a-zA-Z]\\w{3,14}$";

	@Column(name = "USERNAME")
	private final String value;
	
	@SuppressWarnings("unused")
	private Username() {
		this.value = null;
	}
	
	public Username(String value) {
		if(!isValid(value)) {
			throw new DomainException();
		}
		this.value = value;
	}
	
	public static boolean isValid(String value) {
		if(value == null || !value.matches(USERNAME_EXPRESSION)) {
			return false;
		}
		return true;
	}
	
	public String getValue() {
		return value;
	}
}
