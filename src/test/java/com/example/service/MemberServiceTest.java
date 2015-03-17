package com.example.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.Application;
import com.example.model.Member;
import com.example.model.Username;
import com.example.repository.RepositoryConfig;
import com.example.security.SecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class, RepositoryConfig.class, SecurityConfig.class})
public class MemberServiceTest extends AbstractServiceTest {
	
	@Autowired
	private MemberService memberService;
	
	@Test
	public void readMember_isOwner_allow() {
		login(USERNAME_1, PASSWORD_1);
		memberService.readMember();
	}
	
	@Test(expected=AccessDeniedException.class)
	public void readMember_isNotOwner_accessDenied() {
		login(USERNAME_2, PASSWORD_2);
		memberService.readMember();
	}
	
	@Test
	public void updateMember_isOwner_allow() {
		login(USERNAME_1, PASSWORD_1);
		Member member = new Member(new Username(USERNAME_1), FIRST_NAME_1, LAST_NAME_1);
		memberService.updateMember(member);
	}
	
	@Test(expected=AccessDeniedException.class)
	public void updateMember_isNotOwner_accessDenied() {
		login(USERNAME_1, PASSWORD_1);
		Member member = new Member(new Username(USERNAME_2), FIRST_NAME_2, LAST_NAME_2);
		memberService.updateMember(member);
	}

	@Test
	public void createMember_isOwner_allow() {
		login(USERNAME_1, PASSWORD_1);
		Member member = new Member(new Username(USERNAME_1), FIRST_NAME_1, LAST_NAME_1);
		memberService.createMember(member);
	}
	
	@Test(expected=AccessDeniedException.class)
	public void createMember_isNotOwner_accessDenied() {
		login(USERNAME_1, PASSWORD_1);
		Member member = new Member(new Username(USERNAME_2), FIRST_NAME_2, LAST_NAME_2);
		memberService.createMember(member);
	}
	
	@Test
	public void deleteMember_isOwner_allow() {
		login(USERNAME_1, PASSWORD_1);
		memberService.deleteMember(1L);
	}
	
	@Test(expected=AccessDeniedException.class)
	public void deleteMember_isNotOwner_accessDenied() {
		login(USERNAME_1, PASSWORD_1);
		memberService.deleteMember(2L);
	}
}
