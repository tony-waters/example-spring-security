package com.example.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Service;

import com.example.model.Member;
import com.example.model.Username;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	
	SecurityExpressionRoot r;
	MethodSecurityExpressionOperations m;
	
	final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Override
	public Member readMember() {
		logger.info("Member read");
		return new Member(new Username("user1"), "Alice", "Smith");
	}

	@Override
	public void updateMember(Member member) {
		logger.info("Member updated");
	}

	@Override
	public void createMember(Member member) {
		logger.info("Member created");
	}
	
	@Override
	public void deleteMember(Long id) {
		logger.info("Member deleted");
	}

}
