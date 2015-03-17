package com.example.security;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.example.model.Member;


public class PermissionManager implements PermissionEvaluator {
	
	@Resource
	private MemberPermissionEvaluator memberPermissionEvaluator;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if(targetDomainObject instanceof Member) {
			return memberPermissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

		return false;
	}

}
