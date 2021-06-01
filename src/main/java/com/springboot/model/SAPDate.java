package com.springboot.model;

public class SAPDate {
	
	String selectionDate;
	String paymentDate;
	
	public SAPDate(String selectionDate, String paymentDate) {
		
		this.selectionDate = selectionDate;
		this.paymentDate = paymentDate;
	}

	
	
	public String getSelectionDate() {
		return selectionDate;
	}
	public void setSelectionDate(String selectionDate) {
		this.selectionDate = selectionDate;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
}
