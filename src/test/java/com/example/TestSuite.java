package com.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	com.example.service.FooServiceTest.class,
	com.example.service.MemberServiceTest.class
})
public class TestSuite {

}
