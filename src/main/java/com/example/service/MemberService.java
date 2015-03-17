package com.example.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.model.Member;

@PreAuthorize("denyAll")
public interface MemberService {
	
//	@PostAuthorize("returnObject!=null and returnObject.username.equals(principal.username)")
	@PostAuthorize("@memberPermissions.isOwner(returnObject, principal)")
	@PreAuthorize("isAuthenticated()")
	Member readMember();

//	@PreAuthorize("#member.username == principal.username")
//	@PreAuthorize("isAuthenticated() and @memberPermissions.isOwner(#member)")
	@PreAuthorize("isAuthenticated() and @memberPermissions.isOwner(#member, principal)")
	void updateMember(Member member);
	
	@PreAuthorize("isAuthenticated() and hasPermission(#member, 'isOwner')")
	void createMember(Member member);
	
	@PreAuthorize("isAuthenticated() and hasPermission(#id, 'Member', 'isOwner')")
	void deleteMember(Long id);
}
