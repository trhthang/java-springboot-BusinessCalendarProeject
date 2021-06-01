package com.springboot.controller;
/*
 * package com.springboot.controller;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import com.springboot.model.Employee; import com.springboot.model.User;
 * import com.springboot.repository.UserRepository; import
 * com.springboot.service.EmployeeService; import
 * com.springboot.service.UserService;
 * 
 * @Controller public class AppController {
 * 
 * @Autowired private EmployeeService employeeService;
 * 
 * @Autowired private UserService userService;
 * 
 * //display list of employee
 * 
 * @GetMapping("/") public String viewHomePage(Model model) { //
 * model.addAttribute("listEmployees" , employeeService.getAllEmployees() );
 * return "index"; }
 * 
 * @GetMapping("/showNewEvent") public String showNewEmployeeForm(Model model) {
 * // create model attribute to bind form data Employee employee = new
 * Employee(); model.addAttribute("employee", employee); return "new_event"; }
 * 
 * 
 * @PostMapping("/saveEvent") public String
 * saveEmployee(@ModelAttribute("Event") Employee employee) {
 * 
 * //save employee to database
 * 
 * employeeService.saveEmployee(employee); return "redirect:/"; }
 * 
 * @GetMapping("/showFormForUpdate/{id}") public String
 * showFormForUpdate(@PathVariable (value = "id") long id, Model model) {
 * 
 * //get employee from the service Employee employee =
 * employeeService.getEmployeeById(id);
 * 
 * //set employee as a model attribute to pre-polulate the form
 * model.addAttribute("employee" ,employee); return "update_employee"; }
 * 
 * @GetMapping("/deleteEmployee/{id}") public String
 * deleteEmployee(@PathVariable (value = "id") long id) { // call delete
 * employee method
 * 
 * this.employeeService.deleteEmployeeById(id); return "redirect:/"; }
 * 
 * 
 * @GetMapping("/register") public String showSignUpForm(Model model) {
 * model.addAttribute("user", new User());
 * 
 * return "signup_form"; }
 * 
 * @PostMapping("/process_register") public String processRegistration(User
 * user) { //save user to database
 * 
 * userService.saveUser(user); return "register_success"; }
 * 
 * @GetMapping("/list_calendar") public String viewUserList() {
 * 
 * return "users"; }
 * 
 * }
 */