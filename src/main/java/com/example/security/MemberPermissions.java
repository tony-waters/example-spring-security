package com.example.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.model.Member;

@Component("memberPermissions")
public class MemberPermissions {
	
	final Logger logger = LoggerFactory.getLogger(MemberPermissions.class);

	public boolean isOwner(Member targetDomainObject) {
		logger.info("Checking permission on Member", targetDomainObject);
		boolean hasPermission = false;
		if(targetDomainObject != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			hasPermission = userDetails.getUsername().equals(targetDomainObject.getUsername());
			logger.info("Checking username {} has permission to access member {} - {}", userDetails.getUsername(), targetDomainObject,  hasPermission);
		}
		return hasPermission;
	}
	
	public boolean isOwner(Member targetDomainObject, UserDetails principal) {
		logger.info("Checking permission on Member", targetDomainObject);
		boolean hasPermission = false;
		if(targetDomainObject != null) {
			hasPermission = principal.getUsername().equals(targetDomainObject.getUsername());
			logger.info("Checking username {} has permission to access member {} - {}", principal.getUsername(), targetDomainObject,  hasPermission);
		}
		return hasPermission;
	}
}
