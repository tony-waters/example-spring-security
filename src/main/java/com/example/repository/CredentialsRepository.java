package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.security.Credentials;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String>{

}
