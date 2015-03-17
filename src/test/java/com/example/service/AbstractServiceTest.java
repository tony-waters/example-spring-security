package com.example.service;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import java.util.Collection;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;


public abstract class AbstractServiceTest {
	
	protected static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Resource
	private AuthenticationManager authenticationManager;
	
	@Resource
	private DataSource dataSource;
	
	@Resource //(name="credentialsService")
	private UserDetailsService userDetailsService;
	
	protected static final String FIRST_NAME_1 = "Alice";
	protected static final String LAST_NAME_1 = "One";
	protected static final String USERNAME_1 = "user1";
	protected static final String PASSWORD_1 = "password1";
	
	protected static final String FIRST_NAME_2 = "Bob";
	protected static final String LAST_NAME_2 = "Two";
	protected static final String USERNAME_2 = "user2";
	protected static final String PASSWORD_2 = "password2";
	
	protected static final String ROLE_USER = "USER";
	protected static final String ROLE_USER_ID = "1";
	protected static final String ROLE_ADMIN = "ADMIN";
	protected static final String ROLE_ADMIN_ID = "2";
	
	protected static final String PERM_READ_FOO = "PERM_READ_FOO";
	protected static final String PERM_READ_FOO_ID = "1";
	protected static final String PERM_UPDATE_FOO = "PERM_UPDATE_FOO";
	protected static final String PERM_UPDATE_FOO_ID = "2";
	protected static final String PERM_CREATE_FOO = "PERM_CREATE_FOO";
	protected static final String PERM_CREATE_FOO_ID = "3";
	protected static final String PERM_DELETE_FOO = "PERM_DELETE_FOO";
	protected static final String PERM_DELETE_FOO_ID = "4";

   	@Before
   	public void setup() {
   		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
   		
   		Operation operation = sequenceOf(
			deleteAllFrom("ROLE_PERMISSION", "CREDENTIALS_ROLE", "CREDENTIALS", "MEMBER", "PERMISSION", "ROLE"),
			insertInto("MEMBER")				
				.columns("ID", "VERSION", "FIRST_NAME", "LAST_NAME", "USERNAME")
				.values("1","0", FIRST_NAME_1, LAST_NAME_1, USERNAME_1)
				.values("2","0", FIRST_NAME_2, LAST_NAME_2, USERNAME_2)
				.build(),
			insertInto("CREDENTIALS")				
				.columns("USERNAME", "VERSION", "PASSWORD", "ENABLED")
				.values(USERNAME_1,"0", passwordEncoder.encode(PASSWORD_1), true)
				.values(USERNAME_2,"0", passwordEncoder.encode(PASSWORD_2), true)
				.build(),
			insertInto("ROLE")				
				.columns("ID", "VERSION", "NAME")
				.values(ROLE_USER_ID, "0", ROLE_USER)
				.values(ROLE_ADMIN_ID, "0", ROLE_ADMIN)
				.build(),
			insertInto("PERMISSION")				
				.columns("ID", "VERSION", "NAME")
				.values(PERM_READ_FOO_ID, "0", PERM_READ_FOO)
				.values(PERM_UPDATE_FOO_ID, "0", PERM_UPDATE_FOO)
				.values(PERM_CREATE_FOO_ID, "0", PERM_CREATE_FOO)
				.values(PERM_DELETE_FOO_ID, "0", PERM_DELETE_FOO)
				.build(),
			insertInto("CREDENTIALS_ROLE")				
				.columns("USERNAME", "ROLE_ID")
				.values(USERNAME_1, ROLE_USER_ID)
				.values(USERNAME_2, ROLE_USER_ID)
				.values(USERNAME_2, ROLE_ADMIN_ID)
				.build(),
			insertInto("ROLE_PERMISSION")				
				.columns("ROLE_ID", "PERMISSION_ID")
				.values(ROLE_USER_ID, PERM_READ_FOO_ID)
				.values(ROLE_ADMIN_ID, PERM_UPDATE_FOO_ID)
				.values(ROLE_ADMIN_ID, PERM_CREATE_FOO_ID)
				.values(ROLE_ADMIN_ID, PERM_DELETE_FOO_ID)
				.build()
   		);
   		DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
   		dbSetup.launch();
//   		dbSetupTracker.launchIfNecessary(dbSetup);
   	}


	protected void login(String username, String password) {
		Authentication authToken = new UsernamePasswordAuthenticationToken(username, password);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
	
	protected Collection<? extends GrantedAuthority> getAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   		return authentication.getAuthorities();
	}

}
