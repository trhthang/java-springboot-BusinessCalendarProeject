package com.springboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.springboot.model.Recurent_Event;
import com.springboot.model.SAPDate;


public interface EventService {
	Page<Recurent_Event> getAllRecurent_Event(int pageNumber, String keyword, int size);
	void saveEvent(Recurent_Event event);
	Recurent_Event getEventById(long id);
	void deleteEmployeeById(long id);
	Map<String,List<SAPDate>> getSAPDate(long id);
	
	List<Recurent_Event> findByUnion(String unionID);
	Map<String, Map<String, List<SAPDate>>> viewDateUnionAndMonth(String unionID, String month);
}
