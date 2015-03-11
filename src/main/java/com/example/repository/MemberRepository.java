package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

	Member  findByUsername(String username);
}
