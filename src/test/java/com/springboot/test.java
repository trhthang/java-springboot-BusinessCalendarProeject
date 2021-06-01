package com.springboot;

import java.time.DayOfWeek;
//import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

public class test {
	public static void main(String[] args) {
		HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
		Set<Holiday> holidays = m.getHolidays(2021, "");
		System.out.println(holidays);
		System.out.println();
		for(Holiday h : holidays) {
			if(h.getDate().getMonthOfYear() == 5) {
				System.out.println(h.getDate() + " - " + h.getPropertiesKey());
			}
		}
		        
		
	
	}
}
	


