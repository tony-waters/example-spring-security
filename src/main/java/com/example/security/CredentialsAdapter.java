package com.example.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.model.security.Credentials;
import com.example.model.security.Permission;
import com.example.model.security.Role;

public class CredentialsAdapter implements UserDetails {
	
	private Credentials credentials;
	
	public CredentialsAdapter(Credentials credentials) {
		this.credentials = credentials;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(Role role : credentials.getRoles()) {
			for(Permission permission : role.getPermissions()) {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return credentials.getPassword();
	}

	@Override
	public String getUsername() {
		return credentials.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return credentials.isEnabled();
	}

}
