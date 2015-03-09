package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Foo;

@Repository
public interface FooRepository extends JpaRepository<Foo, Long> {

}
