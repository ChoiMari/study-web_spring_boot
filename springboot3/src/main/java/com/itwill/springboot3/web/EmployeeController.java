package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		
		List<Employee> list = employeeService.read();
		model.addAttribute("list", list);
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable(name = "id") Integer id,Model model) {
		log.info("details(id={})",id);
		Employee emp = employeeService.read(id);
		model.addAttribute("emp", emp);
		
		return "/employee/details"; //@PathVariable쓸때는 String으로 리턴 보내야함.

	}
	
}
