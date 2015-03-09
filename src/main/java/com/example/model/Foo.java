package com.example.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="FOO")
public class Foo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long entityId;
	
	@Version
	@Column(name="VERSION")
	private Integer version;

	@Column(name="BAA")
	private String baa;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "BIRTH_MONTH"))
	private Month birthMonth;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "FOO_MONTH"))
	private Month fooMonth;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "start.value", column = @Column(name = "CURRENT_START_MONTH")),
		@AttributeOverride(name = "end.value", column = @Column(name = "CURRENT_END_MONTH"))
	})
	private MonthRange currentMonthRange;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "start.value", column = @Column(name = "PREVIOUS_START_MONTH")),
		@AttributeOverride(name = "end.value", column = @Column(name = "PREVIOUS_END_MONTH"))
	})
	private MonthRange previousMonthRange;
	
	@ElementCollection
	@CollectionTable(name="FOOBAA_MONTH", joinColumns=@JoinColumn(name="FOO_ID"))
	private Collection<Month> months = new ArrayList<Month>();
	
	private Foo() {
		super();
	}
	
	public Foo(Month birthMonth, Month fooMonth, MonthRange currentMonthRange, MonthRange previousMonthRange) {
		this.birthMonth = birthMonth;
		this.fooMonth = fooMonth;
		this.currentMonthRange = currentMonthRange;
		this.previousMonthRange = previousMonthRange;
	}
	
	public void setBaa(String baa) {
		this.baa = baa;
	}

	public Long getEntityId() {
		return entityId;
	}

	public Integer getVersion() {
		return version;
	}

	public String getBaa() {
		return baa;
	}

	public Month getBirthMonth() {
		return birthMonth;
	}

	public Month getFooMonth() {
		return fooMonth;
	}

	public MonthRange getCurrentMonthRange() {
		return currentMonthRange;
	}

	public MonthRange getPreviousMonthRange() {
		return previousMonthRange;
	}

	@Override
	public String toString() {
		return "Foo [entityId=" + entityId + ", version=" + version + ", baa="
				+ baa + ", birthMonth=" + birthMonth + ", fooMonth=" + fooMonth
				+ ", currentMonthRange=" + currentMonthRange
				+ ", previousMonthRange=" + previousMonthRange + "]";
	}
	
	
}
