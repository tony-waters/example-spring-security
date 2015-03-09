package com.example.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.example.exception.DomainException;

@Embeddable
public class MonthRange {

	@AttributeOverride(name = "value", column = @Column(name = "START_MONTH"))
	private final Month start;
	
	@AttributeOverride(name = "value", column = @Column(name = "END_MONTH"))
	private final Month end;

	@SuppressWarnings("unused")
	private MonthRange() {
		this.start = null;
		this.end = null;
	}
	
	public MonthRange(Month start, Month end) {
		if(!isValid(start, end)) {
			throw new DomainException("Not a valid month range");
		}
		this.start = start;
		this.end = end;
	}
	
	public static boolean isValid(Month start, Month end) {
		return start.isBefore(end);
	}

	public Month getStart() {
		return start;
	}

	public Month getEnd() {
		return end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		MonthRange other = (MonthRange) obj;
		if(end == null) {
			if(other.end != null)
				return false;
		} else if(!end.equals(other.end))
			return false;
		if(start == null) {
			if(other.start != null)
				return false;
		} else if(!start.equals(other.start))
			return false;
		return true;
	}
}

