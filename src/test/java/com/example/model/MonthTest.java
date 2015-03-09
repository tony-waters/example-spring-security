package com.example.model;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

import com.example.exception.DomainException;

/**
 * Some basic 'sanity check' tests for Month
 */
public class MonthTest {

	@Test(expected=DomainException.class)
	public void monthStringNull_exceptionThrown() {
		new Month(null);
	}

	@Test(expected=DomainException.class)
	public void monthStringLessThan6_exceptionThrown() {
		new Month("12345");
	}
	
	@Test(expected=DomainException.class)
	public void monthStringContainsNonNumbers_exceptionThrown() {
		new Month("12345!");
	}
	
	@Test(expected=DomainException.class)
	public void monthStringMonthTooBig_exceptionThrown() {
		new Month("200013");
	}
	
	@Test(expected=DomainException.class)
	public void monthStringMonthTooSmall_exceptionThrown() {
		new Month("200000");
	}
	
	@Test(expected=DomainException.class)
	public void monthStringYearTooBig_exceptionThrown() {
		new Month("210111");
	}
	
	@Test(expected=DomainException.class)
	public void monthStringYearTooSmall_exceptionThrown() {
		new Month("189911");
	}
	
	@Test
	public void monthStringLegal() {
		new Month("200012");
	}
	
	@Test
	public void compareMonths_isBefore() {
		Month month = new Month("200001");
		assertThat(month.isBefore(new Month("200002")), is(true));
		assertThat(month.isAfter(new Month("199912")), is(true));
	}
}
