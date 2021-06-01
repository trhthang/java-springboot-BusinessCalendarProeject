package com.springboot.model;

import java.util.List;

public class ShowListCalendar {

	private List<Recurent_Event> listRecurentEvent;
	
	public ShowListCalendar() {
		super();
	}

	public ShowListCalendar(List<Recurent_Event> listRecurentEvent) {
		super();
		this.listRecurentEvent = listRecurentEvent;
	}

	public List<Recurent_Event> getListRecurrent() {
		return listRecurentEvent;
	}

	public void setListRecurrent(List<Recurent_Event> listRecurentEvent) {
		this.listRecurentEvent = listRecurentEvent;
	}

	@Override
	public String toString() {
		return "ListShowCalendar [listRecurrent=" + listRecurentEvent + "]";
	}
	
	
	
}
