package com.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	com.example.model.MonthTest.class,
	com.example.repository.FooRepositoryTest.class
})
public class TestSuite {

}
