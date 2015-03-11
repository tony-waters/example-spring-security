package com.example.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@PreAuthorize("denyAll")
public interface FooService {
	
	@PreAuthorize("hasRole('PERM_READ_FOO')")
	boolean readFoo();
	
	@PreAuthorize("hasRole('PERM_UPDATE_FOO')")
	boolean updateFoo();
	
	@PreAuthorize("hasRole('PERM_CREATE_FOO')")
	boolean createFoo();
	
	@PreAuthorize("hasRole('PERM_DELETE_FOO')")
	boolean deleteFoo();
}
