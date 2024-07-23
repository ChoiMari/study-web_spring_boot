package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		List<Department> list = departmentService.read();
		
		model.addAttribute("list", list);
	}
	
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable(name = "id") Integer id, Model model) {
		log.info("details(id={})",id);
		
		Department dept = departmentService.read(id);
		model.addAttribute("dept", dept);	
		
		return "/department/details";
	}
}
