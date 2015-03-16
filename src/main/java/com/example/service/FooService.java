package com.example.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@PreAuthorize("denyAll")
public interface FooService {
	
	@PreAuthorize("hasAuthority('PERM_READ_FOO')")
	boolean readFoo();
	
	@PreAuthorize("hasAuthority('PERM_UPDATE_FOO')")
	boolean updateFoo();
	
	@PreAuthorize("hasAuthority('PERM_CREATE_FOO')")
	boolean createFoo();
	
	@PreAuthorize("hasAuthority('PERM_DELETE_FOO')")
	boolean deleteFoo();
}
