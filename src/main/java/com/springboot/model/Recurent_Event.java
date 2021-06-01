package com.springboot.model;

import java.sql.Date;

 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "recurent_event")
public class Recurent_Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	
	
	@Column(name = "name")
	private String name;
	

	@Column(name = "start_date")
	private Date start_date;
	

	@Column(name = "end_date")
	private Date end_date;
	
	
	@Column(name = "union_id")
	private long union_id;
	
	@Column(name = "pattern")
	private String pattern;
	
	@Column(name = "preparation_days")
	private Integer preparation_days;
	
	
	@Column(name = "shift_mode")
	private String shift_mode;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getStart_date() {
		return start_date;
	}


	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}


	public Date getEnd_date() {
		return end_date;
	}


	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}


	public long getUnion_id() {
		return union_id;
	}


	public void setUnion_id(long string) {
		this.union_id = string;
	}


	public String getPattern() {
		return pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	public Integer getPreparation_days() {
		return preparation_days;
	}


	public void setPreparation_days(Integer preparation_days) {
		this.preparation_days = preparation_days;
	}


	public String getShift_mode() {
		return shift_mode;
	}


	public void setShift_mode(String shift_mode) {
		this.shift_mode = shift_mode;
	}
	
	
	
	
	
}
