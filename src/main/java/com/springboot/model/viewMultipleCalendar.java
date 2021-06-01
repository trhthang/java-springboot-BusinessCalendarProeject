package com.springboot.model;

public class viewMultipleCalendar {
	private String union_id;
	private String month;
	
	
public viewMultipleCalendar() {
		
	}
	public viewMultipleCalendar(String union_id, String month) {
		super();
		this.union_id = union_id;
		this.month = month;
		
	}
	
	public String getUnion_id() {
		return union_id;
	}
	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "viewMultipleCalendar [union_id=" + union_id + ", month=" + month + "]";
	}
	
	
	
}
