package com.example.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class FooServiceImpl implements FooService {

	@Override
	public boolean readFoo() {
		System.out.println("Foo has been read.");
		return true;
	}

	@Override
	public boolean updateFoo() {
		System.out.println("Foo has been updated.");
		return true;
	}

	@Override
	public boolean createFoo() {
		System.out.println("Foo has been created.");
		return true;
	}

	@Override
	public boolean deleteFoo() {
		System.out.println("Foo has been deleted.");
		return true;
	}

	public boolean doFoo() {
		System.out.println("Foo has been done.");
		return true;
	}
}
