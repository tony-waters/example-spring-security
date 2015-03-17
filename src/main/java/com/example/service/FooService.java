package com.example.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@PreAuthorize("denyAll")
public interface FooService {
	
	@PreAuthorize("isAuthenticated() and hasAuthority('PERM_READ_FOO')")
	boolean readFoo();
	
	@PreAuthorize("isAuthenticated() and hasAuthority('PERM_UPDATE_FOO')")
	boolean updateFoo();
	
	@PreAuthorize("isAuthenticated() and hasAuthority('PERM_CREATE_FOO')")
	boolean createFoo();
	
	@PreAuthorize("isAuthenticated() and hasAuthority('PERM_DELETE_FOO')")
	boolean deleteFoo();
}
