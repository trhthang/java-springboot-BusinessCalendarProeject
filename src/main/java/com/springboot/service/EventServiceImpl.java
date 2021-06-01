package com.springboot.service;

import 	java.util.Optional ;
import java.util.Set;
import java.util.TreeMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.bcel.classfile.ArrayElementValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.model.Recurent_Event;
import com.springboot.model.SAPDate;
import com.springboot.repository.EventRepository;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

@Service
public class EventServiceImpl implements EventService{
	@Autowired
	private EventRepository eventRepository;
	
	

	
	@Override
	public Page<Recurent_Event> getAllRecurent_Event(int pageNumber, String keyword, int size) {
	//public Page<Recurent_Event> getAllRecurent_Event(String keyword) {
		
		
//		return  (List<Recurent_Event>) eventRepository.findAll();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, size);
		
		if(keyword != null) {
			return (Page<Recurent_Event>) eventRepository.findAll(keyword,pageable);
		}
	
		return eventRepository.findAll(pageable);
	
	}
	
	public List<Recurent_Event> findByUnion(String unionID){
		
		return eventRepository.findByUnion(unionID);
		
	}
	
	

	@Override
	public void saveEvent(Recurent_Event Recurent_Event) {
		this.eventRepository.save(Recurent_Event);
		
	}

	@Override
	public Recurent_Event getEventById(long id) {
		Optional<Recurent_Event> optional = eventRepository.findById(id);
		Recurent_Event Recurent_Event = null;
		if(optional.isPresent()) {
			Recurent_Event = optional.get();
		}else {
			throw new RuntimeException("Event not found for id: " + id);
		}
		return Recurent_Event;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.eventRepository.deleteById(id);
		
	}

	@Override
	public Map<String, List<SAPDate>> getSAPDate(long id) {
		
		Recurent_Event event = eventRepository.findById(id).get();
		
		//Lay shiftMode của đối tượng đó
		String shiftMode = event.getShift_mode();
		switch(shiftMode) {
		case "shift-calendar-date" :
			return shift_calendar_date(event);
		case "shift-and-skip-holiday":
			return shift_and_skip_holiday(event);
		case"shift-and-skip-holiday-weekend":
			return shift_and_skip_holiday_weekend(event);
		
		}
		
		return null;
		
	}

	Map<String, List<SAPDate>> 	shift_calendar_date(Recurent_Event event){
		HashMap<String, List<SAPDate>> result = new HashMap<String, List<SAPDate>>();
		
		
		String[] paymentNumArr = event.getPattern().split(",");
        // 5,10,20
		ArrayList<String> selectionNumArr = new ArrayList<>(); 
		// 3,8,18
		for(String item : paymentNumArr) {
			int selectionNum= Integer.parseInt(item) - event.getPreparation_days() - 1;
			selectionNumArr.add(selectionNum + "");
		}

		
		
		for(int month = event.getStart_date().getMonth() + 1 ; month <=  event.getEnd_date().getMonth() + 1; month++) {
			
			
			
			//Tao 1 danh sach cap ngay 
			List<SAPDate> date = new  ArrayList<SAPDate>();
			
			//SHIFMODE : SHIFT CALENDAR DAY
          	for(int i = 0 ; i<selectionNumArr.size() ; i++) {
          		
          		//lay selection date
          		String selectionDate = event.getStart_date().getYear() +1900 + "-" + month + "-" + selectionNumArr.get(i);
          		
          		//lay payment date
          		String paymentDate = event.getStart_date().getYear() + 1900 + "-" + month + "-" + paymentNumArr[i];

          		SAPDate sapDate = new SAPDate(selectionDate,paymentDate);
          		date.add(sapDate);
          	}
          	
          	
          	//SHIFT MODE : SHIFT AND SKIP HOLIDAY
          	
          	switch(month) {
          	case 1:{
          		result.put("January", date);
          		break;
          	}
          	
          	case 2:{
          		result.put("February", date);
          		break;
          	}
          	
          	case 3:{
          		result.put("March", date);
          		break;
          	}
          	
          	case 4:{
          		result.put("April", date);
          		break;
          	}
          	
          	case 5:{
          		result.put("May", date);
          		break;
          	}
          	
          	case 6:{
          		result.put("June", date);
          		break;
          	}
          	
          	case 7:{
          		result.put("July", date);
          		break;
          	}
          	
          	case 8:{
          		result.put("August", date);
          		break;
          	}
          	
          	case 9:{
          		result.put("September", date);
          		break;
          	}
          	 
          	case 10:{
          		result.put("October", date);
          		break;
          	}
          	
          	case 11:{
          		result.put("November", date);
          		break;
          	}
          	
          	case 12:{
          		result.put("December", date);
          		break;
          	}
      	
          	}
          	
		}

		return result;
	}
		
		
		
		
	Map<String, List<SAPDate>> 	shift_and_skip_holiday(Recurent_Event event){
		HashMap<String, List<SAPDate>> result = new HashMap<String, List<SAPDate>>();
		
		
		HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
		Set<Holiday> holidays = m.getHolidays(2021, "");
		
		
		String[] paymentNumArr = event.getPattern().split(",");
		ArrayList<String> selectionNumArr = new ArrayList<>(); 
		
		for(String item : paymentNumArr) {
			int selectionNum = Integer.parseInt(item) - event.getPreparation_days() -1;
			selectionNumArr.add(selectionNum + "");
		}
		
		// start 5 -> 9
		for(int month = event.getStart_date().getMonth() + 1; month <=  event.getEnd_date().getMonth() + 1; month++) {
			//Tao 1 danh sach cap ngay
			List<SAPDate> date = new  ArrayList<SAPDate>();
			// Ngay le trong 1 thang - lay moi ngay 
			ArrayList<String> holidayInMonth = new ArrayList();
			// them ngay vao mang
			for(Holiday dt : holidays) {
				if(dt.getDate().getMonthOfYear() == month)
					holidayInMonth.add(dt.getDate().getDayOfMonth() + "");
			}

          	for(int i = 0 ; i<selectionNumArr.size() ; i++) {
          		int selectionDay = Integer.parseInt(selectionNumArr.get(i));
 
          		int paymentDay = Integer.parseInt(paymentNumArr[i]);
          		for(String dayHoliday : holidayInMonth) {
          			int dayHolidayNum = Integer.parseInt(dayHoliday);
          			
          			if(dayHolidayNum <= paymentDay && dayHolidayNum >= selectionDay) {
          				selectionDay-=1;
          			}
          		}
          		
          		//lay selection date
          		String selectionDate = event.getStart_date().getYear() +1900 + "-" + month + "-" + selectionDay;
          		
          		//lat payment date
          		String paymentDate = event.getStart_date().getYear() + 1900 + "-" + month + "-" + paymentNumArr[i];
          		
          		SAPDate sapDate = new SAPDate(selectionDate,paymentDate);
          		date.add(sapDate);
          	}
          	
          	
          	switch(month) {
          	case 1:{
          		result.put("January", date);
          		break;
          	}
          	
          	case 2:{
          		result.put("February", date);
          		break;
          	}
          	
          	case 3:{
          		result.put("March", date);
          		break;
          	}
          	
          	case 4:{
          		result.put("April", date);
          		break;
          	}
          	
          	case 5:{
          		result.put("May", date);
          		break;
          	}
          	
          	case 6:{
          		result.put("June", date);
          		break;
          	}
          	
          	case 7:{
          		result.put("July", date);
          		break;
          	}
          	
          	case 8:{
          		result.put("August", date);
          		break;
          	}
          	
          	case 9:{
          		result.put("September", date);
          		break;
          	}
          	 
          	case 10:{
          		result.put("October", date);
          		break;
          	}
          	
          	case 11:{
          		result.put("November", date);
          		break;
          	}
          	
          	case 12:{
          		result.put("December", date);
          		break;
          	}
      	
          	}
          	
		}

		return result;
	}
	
	
		
		
	
	Map<String, List<SAPDate>> 	shift_and_skip_holiday_weekend(Recurent_Event event){

			HashMap<String, List<SAPDate>> result = new HashMap<String, List<SAPDate>>();
			
			// Lay ngay holiday 
			HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
			Set<Holiday> holidays = m.getHolidays(2021, "");
			
			
			String[] paymentNumArr = event.getPattern().split(",");
			ArrayList<String> selectionNumArr = new ArrayList<>(); 

			
			// start 5 -> 9
			for(int month = event.getStart_date().getMonth() + 1; month <=  event.getEnd_date().getMonth() + 1; month++) {
				
				//Tao 1 danh sach cap ngay
				List<SAPDate> date = new  ArrayList<SAPDate>();
				// Ngay le trong 1 thang - lay moi ngay 
				ArrayList<String> holidayInMonth = new ArrayList();
				// them ngay vao mang
				for(Holiday dt : holidays) {
					if(dt.getDate().getMonthOfYear() == month)
						holidayInMonth.add(dt.getDate().getDayOfMonth() + "");
				}
				int prepareDay = event.getPreparation_days();// khong thay doi sau moi thang
			  	for(int i = 0 ; i<paymentNumArr.length ; i++) {
			  		int prepareD = prepareDay;
	          		int paymentDay = Integer.parseInt(paymentNumArr[i]);
	          		String paymentDate = event.getStart_date().getYear() + 1900 + "-" + month + "-" + paymentNumArr[i];
	          		Date selectionDate = new Date(event.getStart_date().getYear() + 1900 + "/" + month + "/" +paymentDay);
	          		selectionDate.setTime(selectionDate.getTime() - 86000000);
	          		while(true) {
	       
	          			boolean check = false;
	          			 if(checkWeekend(selectionDate.getDate(), month, (selectionDate.getYear() + 1900))) {
	                    	check = true;
	                    }
	                    
	                    for(String dayHoliday : holidayInMonth) {
	              			int dayHolidayNum = Integer.parseInt(dayHoliday);
	              			
	              			if(dayHolidayNum == selectionDate.getDate()) {
	              				check = true;
	              			}
	              		}
	          			if(!check) {
	          				prepareD--;
	          			}
	          			   
	          			long timeInMili = selectionDate.getTime();
	          			
	          				selectionDate.setTime(timeInMili - 86000000);		
	          			if(prepareD == 0) break;
	          		}
	          		
	          		
	          		while(true) {
	          			boolean check = false;
	          			 if(checkWeekend(selectionDate.getDate(), month, (selectionDate.getYear() + 1900))) {
	                    	check = true;
	                    }
	                    
	                    for(String dayHoliday : holidayInMonth) {
	              			int dayHolidayNum = Integer.parseInt(dayHoliday);
	              			
	              			if(dayHolidayNum == selectionDate.getDate()) {
	              				check = true;
	              			}
	              		}
	          			if(!check) {
	          				break;
	          			}
	          			long timeInMili = selectionDate.getTime();
	          			
          				selectionDate.setTime(timeInMili - 86000000);
	          		}
	          		

	          		// selection date
	          		String selectionD = selectionDate.getYear() + 1900 + "-" + month + "-" + selectionDate.getDate();
	          		
	          		//lat payment date
	          		//String paymentDate = event.getStart_date().getYear() + 1900 + "-" + month + "-" + paymentNumArr[i];
	          		
	          		SAPDate sapDate = new SAPDate(selectionD,paymentDate);
	          		date.add(sapDate);
	          	}
			
				switch(month) {
	          	case 1:{
	          		result.put("January", date);
	          		break;
	          	}
	          	
	          	case 2:{
	          		result.put("February", date);
	          		break;
	          	}
	          	
	          	case 3:{
	          		result.put("March", date);
	          		break;
	          	}
	          	
	          	case 4:{
	          		result.put("April", date);
	          		break;
	          	}
	          	
	          	case 5:{
	          		result.put("May", date);
	          		break;
	          	}
	          	
	          	case 6:{
	          		result.put("June", date);
	          		break;
	          	}
	          	
	          	case 7:{
	          		result.put("July", date);
	          		break;
	          	}
	          	
	          	case 8:{
	          		result.put("August", date);
	          		break;
	          	}
	          	
	          	case 9:{
	          		result.put("September", date);
	          		break;
	          	}
	          	 
	          	case 10:{
	          		result.put("October", date);
	          		break;
	          	}
	          	
	          	case 11:{
	          		result.put("November", date);
	          		break;
	          	}
	          	
	          	case 12:{
	          		result.put("December", date);
	          		break;
	          	}
	      	
	          	}
	          	
				
				
			}	
			
			
		return result;
	}
	
	
	private boolean checkWeekend(int day, int month, int year) {
		 LocalDate ld = LocalDate.of(year, month, day);
		   int days = ld.getDayOfWeek().getValue();
		   if(days == 6 || days == 7 )
			   return true;
		   return false;
	}

	
	public Map<String, Map<String, List<SAPDate>>> viewDateUnionAndMonth(String unionID, String month){
		Map<String, Map<String, List<SAPDate>>> result = null;
		String[] monthArr = month.split(",");
		//Lay event theo union (1)
		List<String> union = Arrays.asList(unionID.split(","));
		List<Recurent_Event> eventByUnionID = eventRepository.findByUnionIdANDMonth(union);
		
		List<Recurent_Event> eventByUnionAndMonth = eventByUnionID; //(2)
		// Loc trong danh dach (1) - chi con co 1 thang hop le thi lay vao list (2)
//		for(Recurent_Event event : eventByUnionID) {
//			int sMonth = event.getStart_date().getMonth();
//			int eMonth = event.getEnd_date().getMonth();
//			for(int i = 0; i< monthArr.length ; i++) {
//				int monthNum = Integer.parseInt(monthArr[i]);
//				if(monthNum >= sMonth && monthNum  <= eMonth) {
//					if(eventByUnionAndMonth == null) eventByUnionAndMonth = new ArrayList<Recurent_Event>();
//					eventByUnionAndMonth.add(event);
//					break;
//				}
//			}
//		}
		
       if(eventByUnionID == null) return null;
       System.out.println("Size Union:" + eventByUnionID.size());
       System.out.println("Size for Month: " + eventByUnionAndMonth.size());
		// duyet theo tung thang -> in ra het cac tieu bang trong thang 5
		for(int i = 0 ; i < monthArr.length ; i++) {// cai thang da chon bang so
			// Lay het cac ngay da lam o tren trong cai danh sach (2)
			String monthInEng = "";
			// doi thang  = so qua chu gan monthInEng
			switch(Integer.parseInt(monthArr[i])) {
          	case 1:{
          		monthInEng ="January";
          		break;
          	}
          	
          	case 2:{
          		monthInEng ="February";
          		break;
          	}
          	
          	case 3:{
          		monthInEng ="March";
          		break;
          	}
          	
          	case 4:{
          		monthInEng ="April";
          		break;
          	}
          	
          	case 5:{
          		monthInEng ="May";
          		break;
          	}
          	
          	case 6:{
          		monthInEng ="June";
          		break;
          	}
          	
          	case 7:{
          		monthInEng ="July";
          		break;
          	}
          	
          	case 8:{
          		monthInEng ="August";
          		break;
          	}
          	
          	case 9:{
          		monthInEng ="September";
          		break;
          	}
          	 
          	case 10:{
          		monthInEng ="October";
          		break;
          	}
          	
          	case 11:{
          		monthInEng ="November";
          		break;
          	}
          	
          	case 12:{
          		monthInEng ="December";
          		break;
          	}
      	
          	}
			System.out.println(monthInEng);
			for(Recurent_Event event: eventByUnionID) {
				// Map nay la | Thang - Danh sach cac ngay cua event (1)
				Map<String, List<SAPDate>> dateDetails = getSAPDate(event.getId());
				// new cai de luu ket qua  -lich tong hop
				if(result == null) result = new HashMap<String, Map<String,List<SAPDate>>>();// |Thang | Union ID | List DS Ngay cua Union va cai thang 
				// MAy | 1 | .....
				// May | 1 | .....
				// May | 3 |..... 
				// June | 3| .....
				List<SAPDate> listDate = null;
				if(dateDetails.containsKey(monthInEng)) {// neu trong 1 co cai thang minh chon
				 listDate = dateDetails.get(monthInEng); // lay list date theo thang dang duyet
				 String unionId = event.getUnion_id()+"";// lay id union cua event dang duyet
				
				Map<String, List<SAPDate>> date = null;
				if(result.containsKey(monthInEng)) {// kiem tra xem ketqua co thang do chua
					date = result.get(monthInEng);// danh sach union ID
						if(date.containsKey(unionId)) {// kiem tra union do co trong cai ket qua chua
							listDate.addAll(date.get(unionId)); // co roi thi lay danh sach ra add them cac ngay vao
					}
				} 
				 
				 
				if(date == null)
					date = new TreeMap<String, List<SAPDate>>();
					date.put(unionId, listDate);
					result.put(monthInEng, date );
				
				}
			}
		}
		
		for(String monthh : result.keySet()) {
			System.out.println("M:" + monthh);
			Map<String, List<SAPDate>> unionDate = result.get(monthh);
			if(unionDate != null) {
				for(String unionIDd : unionDate.keySet()) {
					System.out.println("\tUnion:" + unionIDd);
					List<SAPDate> list = unionDate.get(unionIDd);
					for(SAPDate date : list) {
						System.out.println("\t\t" + date.getSelectionDate() + " - " + date.getPaymentDate());
					}
				}
			} else{
			System.out.println("NULL");
			}
		}
		
		return result;
	}
	

}
