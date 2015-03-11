package com.example.service;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.Application;
import com.example.repository.RepositoryConfig;
import com.example.security.SecurityConfig;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class, RepositoryConfig.class, SecurityConfig.class})
public class FooServiceTest {
	
	static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Resource
	AuthenticationManager authenticationManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private FooService fooService;
	
	private static final String FIRST_NAME_1 = "Alice";
	private static final String LAST_NAME_1 = "One";
	private static final String USERNAME_1 = "user1";
	private static final String PASSWORD_1 = "password1";
	
	private static final String FIRST_NAME_2 = "Bob";
	private static final String LAST_NAME_2 = "Two";
	private static final String USERNAME_2 = "user2";
	private static final String PASSWORD_2 = "password2";
	
	private static final String ROLE_USER = "USER";
	private static final String ROLE_USER_ID = "1";
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_ADMIN_ID = "2";
	
	private static final String PERM_READ_FOO = "PERM_READ_FOO";
	private static final String PERM_READ_FOO_ID = "1";
	private static final String PERM_UPDATE_FOO = "PERM_UPDATE_FOO";
	private static final String PERM_UPDATE_FOO_ID = "2";
	private static final String PERM_CREATE_FOO = "PERM_CREATE_FOO";
	private static final String PERM_CREATE_FOO_ID = "3";
	private static final String PERM_DELETE_FOO = "PERM_DELETE_FOO";
	private static final String PERM_DELETE_FOO_ID = "4";
	
   	@Before
   	public void setup() {
   		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
   		
   		Operation operation = sequenceOf(
			deleteAllFrom("ROLE_PERMISSION", "MEMBER_ROLE", "CREDENTIALS", "MEMBER", "PERMISSION", "ROLE"),
			insertInto("CREDENTIALS")				
				.columns("USERNAME", "VERSION", "PASSWORD", "ENABLED")
				.values(USERNAME_1,"0", passwordEncoder.encode(PASSWORD_1), true)
				.values(USERNAME_2,"0", passwordEncoder.encode(PASSWORD_2), true)
				.build(),
			insertInto("MEMBER")				
				.columns("ID", "VERSION", "FIRST_NAME", "LAST_NAME", "USERNAME")
				.values("1","0", FIRST_NAME_1, LAST_NAME_1, USERNAME_1)
				.values("2","0", FIRST_NAME_2, LAST_NAME_2, USERNAME_2)
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
			insertInto("MEMBER_ROLE")				
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
   	
	@Test
	public void user_permissions() {
		login(USERNAME_1, PASSWORD_1);
		assertThat(fooService.readFoo()).isTrue();

   		assertThat(getAuthorities()).extracting("authority").containsOnly(PERM_READ_FOO);
	}
	
	@Test
	public void admin_permissions() {
		login(USERNAME_2, PASSWORD_2);
		assertThat(fooService.readFoo()).isTrue();

   		assertThat(getAuthorities()).extracting("authority").containsOnly(PERM_READ_FOO, PERM_UPDATE_FOO, PERM_CREATE_FOO, PERM_DELETE_FOO);
	}

	@Test
	public void user_readFoo_ok() {
		login(USERNAME_1, PASSWORD_1);
		assertThat(fooService.readFoo()).isTrue();
	}
	
	@Test(expected=AccessDeniedException.class)
	public void user_updateFoo_accessDenied() {
		login(USERNAME_1, PASSWORD_1);
		fooService.updateFoo();
	}
	
	@Test(expected=AccessDeniedException.class)
	public void user_createFoo_accessDenied() {
		login(USERNAME_1, PASSWORD_1);
		fooService.createFoo();
	}
	
	@Test(expected=AccessDeniedException.class)
	public void user_deleteFoo_accessDenied() {
		login(USERNAME_1, PASSWORD_1);
		fooService.deleteFoo();
	}
	
	@Test
	public void admin_readFoo_ok() {
		login(USERNAME_2, PASSWORD_2);
		assertThat(fooService.readFoo()).isTrue();
	}
	
	@Test
	public void admin_updateFoo_ok() {
		login(USERNAME_2, PASSWORD_2);
		assertThat(fooService.updateFoo()).isTrue();
	}
	
	@Test
	public void admin_createFoo_ok() {
		login(USERNAME_2, PASSWORD_2);
		assertThat(fooService.createFoo()).isTrue();
	}
	
	@Test
	public void admin_deleteFoo_ok() {
		login(USERNAME_2, PASSWORD_2);
		assertThat(fooService.deleteFoo()).isTrue();
	}
	
	private void login(String username, String password) {
		Authentication authToken = new UsernamePasswordAuthenticationToken(username, password);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   		return authentication.getAuthorities();
	}

}
