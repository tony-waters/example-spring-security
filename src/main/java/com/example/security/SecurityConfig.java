package com.example.security;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	@Qualifier(value="credentialsService")
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}
	
//    @Bean
//    public PermissionEvaluator permissionEvaluator() {
//        MemberPermissionEvaluator bean = new MemberPermissionEvaluator();
//        return bean;
//    }
    
    private Map<String, PermissionEvaluator> getPermissionMap() {
    	Map<String, PermissionEvaluator> permissionsMap = new HashMap<String, PermissionEvaluator>();
//    	permissionsMap.put("IS_OWNER", )
    	return permissionsMap;
    }

}
