package com.example.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.Application;
import com.example.repository.RepositoryConfig;
import com.example.security.SecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class, RepositoryConfig.class, SecurityConfig.class})
public class FooServiceTest extends AbstractServiceTest {
	
	@Autowired
	private FooService fooService;
   	
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
	
}
