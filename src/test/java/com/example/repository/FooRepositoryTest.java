package com.example.repository;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.example.Application;
import com.example.model.Foo;
import com.example.model.Month;
import com.example.model.MonthRange;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;

/**
 * Some basic tests to check CRUD works
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class, RepositoryConfig.class})
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class FooRepositoryTest {
	
	static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private FooRepository fooRepository;
	
	private static final String BAA_1 = "Baa";
	private static final String BIRTH_MONTH_1 = "196601";
	private static final String FOO_MONTH_1 = "200001";
	private static final String CURRENT_START_MONTH_1 = "201501";
	private static final String CURRENT_END_MONTH_1 = "201512";
	private static final String PREVIOUS_START_MONTH_1 = "201401";
	private static final String PREVIOUS_END_MONTH_1 = "201412";
	
	private static final String BAA_2 = "Baa";
	private static final String BIRTH_MONTH_2 = "196701";
	private static final String FOO_MONTH_2 = "200002";
	private static final String CURRENT_START_MONTH_2 = "201301";
	private static final String CURRENT_END_MONTH_2 = "201312";
	private static final String PREVIOUS_START_MONTH_2 = "201201";
	private static final String PREVIOUS_END_MONTH_2 = "201212";
	
	private static final String BIRTH_MONTH_3 = "202501";
	private static final String FOO_MONTH_3 = "202502";
	private static final String CURRENT_START_MONTH_3 = "202601";
	private static final String CURRENT_END_MONTH_3 = "202612";
	private static final String PREVIOUS_START_MONTH_3 = "202501";
	private static final String PREVIOUS_END_MONTH_3 = "202512";
	
	private static final String FOOBAA_MONTH_1 = "202501";
	private static final String FOOBAA_MONTH_2 = "202502";
	private static final String FOOBAA_MONTH_3 = "202503";
	private static final String FOOBAA_MONTH_4 = "202504";
	private static final String FOOBAA_MONTH_5 = "202505";
	
   	@Before
   	public void setup() {
   		Operation operation = sequenceOf(
			deleteAllFrom("FOOBAA_MONTH", "FOO"),
			insertInto("FOO")				
				.columns("ID", "VERSION", "BAA", "BIRTH_MONTH", "FOO_MONTH", "CURRENT_START_MONTH", "CURRENT_END_MONTH", "PREVIOUS_START_MONTH", "PREVIOUS_END_MONTH")
				.values("1","0", BAA_1, BIRTH_MONTH_1, FOO_MONTH_1, CURRENT_START_MONTH_1, CURRENT_END_MONTH_1, PREVIOUS_START_MONTH_1, PREVIOUS_END_MONTH_1)
				.values("2","0", BAA_2, BIRTH_MONTH_2, FOO_MONTH_2, CURRENT_START_MONTH_2, CURRENT_END_MONTH_2, PREVIOUS_START_MONTH_2, PREVIOUS_END_MONTH_2)
				.build(),
			insertInto("FOOBAA_MONTH")				
				.columns("FOO_ID", "MONTH")
				.values("1", FOOBAA_MONTH_1)
				.values("1", FOOBAA_MONTH_2)
				.values("1", FOOBAA_MONTH_3)
				.values("2", FOOBAA_MONTH_4)
				.build()
   		);
   		DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
   		dbSetupTracker.launchIfNecessary(dbSetup);
   	}
   	
   	@Test
   	public void readFoo() {
   		dbSetupTracker.skipNextLaunch();
   		Iterable<Foo> result = fooRepository.findAll();
   		
   		assertThat(result)
   			.extracting("birthMonth", "fooMonth", "currentMonthRange", "previousMonthRange")
   			.containsOnly(
   					tuple(
   						new Month(BIRTH_MONTH_1), 
   						new Month(FOO_MONTH_1), 
   						new MonthRange(new Month(CURRENT_START_MONTH_1), new Month(CURRENT_END_MONTH_1)),
   						new MonthRange(new Month(PREVIOUS_START_MONTH_1), new Month(PREVIOUS_END_MONTH_1))
   					),
   					tuple(
   	   						new Month(BIRTH_MONTH_2), 
   	   						new Month(FOO_MONTH_2), 
   	   						new MonthRange(new Month(CURRENT_START_MONTH_2), new Month(CURRENT_END_MONTH_2)),
   	   						new MonthRange(new Month(PREVIOUS_START_MONTH_2), new Month(PREVIOUS_END_MONTH_2))
   					)
   			);
   	}

	@Test
	public void createFoo() {
		Foo foo = new Foo(
			new Month(BIRTH_MONTH_3), 
			new Month(FOO_MONTH_3), 
			new MonthRange(new Month(CURRENT_START_MONTH_3), new Month(CURRENT_END_MONTH_3)),
			new MonthRange(new Month(PREVIOUS_START_MONTH_3), new Month(PREVIOUS_END_MONTH_3))
		);
		fooRepository.save(foo);
		
		Iterable<Foo> result1 = fooRepository.findAll();
		
   		assertThat(result1)
   			.extracting("birthMonth", "fooMonth", "currentMonthRange", "previousMonthRange")
   			.contains(
   					tuple(
						new Month(BIRTH_MONTH_3), 
						new Month(FOO_MONTH_3), 
						new MonthRange(new Month(CURRENT_START_MONTH_3), new Month(CURRENT_END_MONTH_3)),
						new MonthRange(new Month(PREVIOUS_START_MONTH_3), new Month(PREVIOUS_END_MONTH_3))
   					)
   			);
	}
	
	@Test
	public void updateFoo() {
		Foo foo = fooRepository.findOne(1L);
		foo.setBaa("foo");
		fooRepository.save(foo);
		
//		fooRepository.flush();

		foo = fooRepository.findOne(1L);
		
		assertThat(foo.getBaa()).isEqualTo("foo");
		assertThat(foo.getBirthMonth()).isEqualTo(new Month(BIRTH_MONTH_1));
   		assertThat(foo.getFooMonth()).isEqualTo(new Month(FOO_MONTH_1));
   		assertThat(foo.getCurrentMonthRange()).isEqualTo(new MonthRange(new Month(CURRENT_START_MONTH_1), new Month(CURRENT_END_MONTH_1)));
   		assertThat(foo.getPreviousMonthRange()).isEqualTo(new MonthRange(new Month(PREVIOUS_START_MONTH_1), new Month(PREVIOUS_END_MONTH_1)));
	}
	
	@Test
	public void deleteFoo() {
		Foo foo = fooRepository.findOne(1L);
		fooRepository.delete(foo);
		
   		Iterable<Foo> result1 = fooRepository.findAll();
   		
   		assertThat(result1)
   			.extracting("birthMonth", "fooMonth", "currentMonthRange", "previousMonthRange")
   			.containsOnly(
   					tuple(
   	   						new Month(BIRTH_MONTH_2), 
   	   						new Month(FOO_MONTH_2), 
   	   						new MonthRange(new Month(CURRENT_START_MONTH_2), new Month(CURRENT_END_MONTH_2)),
   	   						new MonthRange(new Month(PREVIOUS_START_MONTH_2), new Month(PREVIOUS_END_MONTH_2))
   					)
   			);
	}

}
