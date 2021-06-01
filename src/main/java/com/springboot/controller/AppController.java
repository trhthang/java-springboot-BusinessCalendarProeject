package com.springboot.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.model.Recurent_Event;
import com.springboot.model.SAPDate;
import com.springboot.model.Union_Info;
import com.springboot.model.User;
import com.springboot.model.viewMultipleCalendar;
import com.springboot.repository.EventRepository;
import com.springboot.repository.UnionInfoRepository;

import com.springboot.service.EventService;
import com.springboot.service.UserService;

@Controller
public class AppController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userSerice;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UnionInfoRepository unionInfoRepo;
	
	
	@GetMapping("/")
	public String viewHomePage(Model model , HttpServletRequest request
			
			) {
		Locale currentLocale = request.getLocale();
		String countryCode = currentLocale.getCountry();
		String countryName = currentLocale.getDisplayCountry();
		
		String langCode = currentLocale.getLanguage();
		String langName = currentLocale.getDisplayLanguage();

		String[] languages = Locale.getISOLanguages();

		String keyword = null;

		return listByPage(model, 1,keyword, 5);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword,@RequestParam(name = "size", required = false, defaultValue = "5") int size) {
		
		
		Page<Recurent_Event> page = eventService.getAllRecurent_Event(currentPage,keyword,size);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		List<Recurent_Event> listEvents= page.getContent();
		
		
//		ArrayList<Union_Info> unionInfoList = new ArrayList<>(); 
//		for(Recurent_Event event : listEvents) {
//			for (Union_Info unionInfo : unionInfoList) {
//				unionInfo =  unionInfoRepo.findById(event.getUnion_id()).get();
//				unionInfoList.add(unionInfo);
//			}
//		}
//	
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("listEvents" , listEvents);
//		model.addAttribute("unionInfoList" , unionInfoList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("size", size);
		
		
		List<Integer> listMonths = ListMonth();
		   
		model.addAttribute("listMonths", listMonths);
		model.addAttribute("viewMultipleCalendar", new viewMultipleCalendar());
		
		return "index";

	}
	
//	@GetMapping("/")
//	public String index() {
//		return "redirect:/listEvent";
//		
//	}
//	
//	
//	@GetMapping("/listEvent")
//	public String listEvent(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
//			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
//			@RequestParam(name = "lang", required = false, defaultValue = "en") String lang,Model model) {
//		
//		Page<Recurent_Event> pageEvent = eventRepository.findAll(PageRequest.of(page-1, size));
//		List<Union_Info> listUnion = unionInfoRepo.findAll();
//		List<Recurent_Event> listEvents = eventService.getAllRecurent_Event();
//		
//		return "index";
//	}
	
	
	 @GetMapping("/showNewEvent") 
	 public String showNewEventForm(Model model) {
		 Recurent_Event recurentEvent = new Recurent_Event();
		 
		 model.addAttribute("recurentEvent" , recurentEvent);
		 return "new_event";
	 }
	 
	 @PostMapping("/saveEvent")
	 public String saveEvent(@ModelAttribute("recurentEvent") Recurent_Event recurentEvent) {
		 eventService.saveEvent(recurentEvent);
		 return "redirect:/";
	 }
	 
	 
	 @GetMapping("/showFormForUpdate/{id}")
	 public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		 //get event from the service
		 Recurent_Event event = eventService.getEventById(id);
		 
		 //set event as a model attribute to pre-populate the form
		 model.addAttribute("event" , event);
		 return "update_event";
	 }
	 
	 @GetMapping("/deleteEvent/{id}")
	 public String deleteEvent(@PathVariable (value = "id") long id) {
		 this.eventService.deleteEmployeeById(id);
		 return "redirect:/";
	 }
	 
	 @GetMapping("/showEventView/{id}")
	 public String showEventView(@PathVariable (value = "id") long id, Model model) {
		 //get event from the service
		 Recurent_Event event = eventService.getEventById(id);
		 
		 Union_Info unionInfo =  unionInfoRepo.findById(event.getUnion_id()).get();
		 
		 
		 Map<String, List<SAPDate>> getSAPDate =  eventService.getSAPDate(id);
		 model.addAttribute("SAPDate" , getSAPDate );
		 model.addAttribute("union" , unionInfo.getName() );

		 return "detail_event";
	 }
	 
	 
	 @GetMapping("/showAllCalendar")
	 public String showAllCalendar(Model model) {
		 
//		model.addAttribute("listEvents" , eventService.getAllRecurent_Event());		 
		 
		return "all_calendar";
		 
		
	}
	 
	 
	  @GetMapping("/register") public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		 
		return "signup_form"; }
	  
	   @PostMapping("/process_register") public String processRegistration(User
			  user) { //save user to database
			  
		   userSerice.saveUser(user); return "register_success"; 
	}
	   
	   @GetMapping("/login")
	   public String showLoginPage() {
		   org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   if(authentication == null  || authentication instanceof AnonymousAuthenticationToken) {
			   return "/login";
		   }
		   return "redirect:/";
	   }
	   
	   
	   @PostMapping("/viewMultipleUnion")
	   public String viewMultipleUnion(@Param("searchUnion") String searchUnion,
			   @Param("searchMonth") String searchMonth,Model model,
			   @ModelAttribute("viewMultipleCalendar") viewMultipleCalendar multipleCalendar) {
		   
		  // List<Union_Info> listUnion = unionInfoRepo.findAll();
		   List<Integer> listMonths = ListMonth();
		   Map<String, Map<String, List<SAPDate>>> result = eventService.viewDateUnionAndMonth(multipleCalendar.getUnion_id(), multipleCalendar.getMonth());
		   List<String> sUnion = Arrays.asList(multipleCalendar.getUnion_id().split(","));
		   List<Union_Info> selectedUnion = unionInfoRepo.getSelectedUniton(sUnion);
		   model.addAttribute("result", result);
		   model.addAttribute("listMonths", listMonths);   
		   System.out.println("Mang union: " + selectedUnion.size());
		   //model.addAttribute("viewMultipleCalendar", new viewMultipleCalendar());
		   model.addAttribute("listUnion", selectedUnion);
		   for(Union_Info ui : selectedUnion) {
			   System.out.println(ui.getName() + "");
		   }
		
		   
		   
		   return "viewMultipleUnion";
	   }
	   
		//Create list Month
		public List<Integer> ListMonth(){
			List<Integer> listMonths = new ArrayList<Integer>();
			for(int i = 1; i <= 12; i++) {
				listMonths.add(i);
			}
			return listMonths;
		}
		


}

