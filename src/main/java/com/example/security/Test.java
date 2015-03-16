package com.example.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class Test extends SecurityExpressionRoot {

	public Test(Authentication authentication) {
		super(authentication);
		// TODO Auto-generated constructor stub
	}
	
	

}
