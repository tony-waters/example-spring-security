package com.example.security;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.model.Member;
import com.example.repository.MemberRepository;

@Component
public class MemberPermissionEvaluator implements PermissionEvaluator {
	
	final Logger logger = LoggerFactory.getLogger(MemberPermissionEvaluator.class);
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		boolean hasPermission = false;
		if(targetDomainObject != null && targetDomainObject instanceof Member) {
			Member member = (Member)targetDomainObject;
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			hasPermission = userDetails.getUsername().equals(member.getUsername());
			logger.info("Checking username {} has permission to access member {} - {}", userDetails.getUsername(), targetDomainObject,  hasPermission);
		}
		return hasPermission;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		boolean hasPermission = false;
		if(targetId != null && targetId instanceof Long && "Member".equals(targetType)) {
			Long id = (Long)targetId;
			Member member = memberRepository.findOne(id);
			if(member != null) {
				UserDetails userDetails = (UserDetails)authentication.getPrincipal();
				hasPermission = userDetails.getUsername().equals(member.getUsername());
				logger.info("Checking username {} has permission to access member {} - {}", userDetails.getUsername(), member,  hasPermission);
			}
		}
		return hasPermission;
	}
}
