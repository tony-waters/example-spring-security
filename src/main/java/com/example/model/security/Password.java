package com.example.model.security;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.example.model.DomainException;

@Embeddable
public class Password {
	
	private static final String PASSWORD_EXPRESSION = "^[a-zA-Z]\\w{3,14}$";

	@Column(name = "PASSWORD")
	private final String value;
	
	@SuppressWarnings("unused")
	private Password() {
		this.value = null;
	}
	
	public Password(String value) {
		if(!isValid(value)) {
			throw new DomainException();
		}
		this.value = value;
	}
	
	public static boolean isValid(String value) {
		if(value == null || !value.matches(PASSWORD_EXPRESSION)) {
			return false;
		}
		return true;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Password other = (Password) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
