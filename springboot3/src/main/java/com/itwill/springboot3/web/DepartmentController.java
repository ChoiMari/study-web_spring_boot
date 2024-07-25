package com.itwill.springboot3.web;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
	
	private final DepartmentService deptSvc;
	
	@GetMapping("/list")
	public void list(Model model, @RequestParam(name = "p", defaultValue = "0") int pageNo) {
		log.info("list(pageNo={})",pageNo);
		Page<Department> list = deptSvc.read(pageNo, Sort.by("id"));
		
		model.addAttribute("page", list);
	}
	
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable(name = "id") Integer id, Model model) {
		log.info("details(id={})",id);
		
		Department dept = deptSvc.read(id);
		model.addAttribute("dept", dept);	
		
		return "/department/details";
	}
}
