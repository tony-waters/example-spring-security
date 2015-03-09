package com.example.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.example.exception.DomainException;

/**
 * Represents a month in a year between 1900 and 2100
 */
@Embeddable
public final class Month implements Comparable<Month> {
	
	private static final int MIN_YEAR = 1900;
	
	private static final int MAX_YEAR = 2100;
	
	@Column(name="MONTH")
	private final String value;
	
	// for JPAs benefit
	@SuppressWarnings("unused")
	private Month() {
		this.value = null;
	}

	public Month(String value) {
		if(!isValid(value)) {
			throw new DomainException("Not a valid month " + value);
		}
		this.value = value;
	}

	public static boolean isValid(String value) {
		if (value == null 
				|| !isInteger(value) 
				|| value.length() != 6
				|| getYear(value) < MIN_YEAR
				|| getYear(value) > MAX_YEAR
				|| getMonth(value) < 1
				|| getMonth(value) >12) {
			return false;
		}
		return true;
	}
	
	public String getMonthAsString() {
		return value;
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private static int getYear(String year) {
		return Integer.parseInt(year.substring(0, 4));
	}

	private static int getMonth(String months) {
		return Integer.parseInt(months.substring(4, 6));
	}
	
	public boolean isBefore(Month other) {
		return this.compareTo(other) == -1;
	}
	
	public boolean isAfter(Month other) {
		return this.compareTo(other) == 1;
	}

	@Override
	public int compareTo(Month other) {
		if(other == null) {
			throw new NullPointerException();
		}
		Integer thisMonth = Integer.valueOf(this.value);
		Integer otherMonth = Integer.valueOf(other.getMonthAsString());
		if(thisMonth < otherMonth) {
			return -1;
		} else if(thisMonth > otherMonth) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Month other = (Month) obj;
		if(value == null) {
			if(other.value != null) return false;
		} else if(!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Month [value=" + value + "]";
	}
}
