package com.example.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.Username;
import com.example.model.security.Credentials;
import com.example.repository.CredentialsRepository;
import com.example.security.CredentialsAdapter;

@Service("authService")
@Transactional
public class CredentialsService implements UserDetailsService {
	
	@Resource
	private CredentialsRepository credentialsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Credentials credentials = credentialsRepository.findOne(new Username(username));
		if(credentials == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CredentialsAdapter(credentials);
	}
}
