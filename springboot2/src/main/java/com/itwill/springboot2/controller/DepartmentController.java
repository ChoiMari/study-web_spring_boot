package com.itwill.springboot2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot2.domain.Department;
import com.itwill.springboot2.domain.Employee;
import com.itwill.springboot2.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/department")
@Slf4j
public class DepartmentController {
	
    private final DepartmentService deptSvc;
    
    @GetMapping("/list")
    public void list(Model model) {
        log.info("list()");
        
        List<Department> list = deptSvc.read();
        model.addAttribute("departments", list);
    }
    
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        log.info("details(id={})", id);
        
        Department dept = deptSvc.read(id);
        model.addAttribute("department", dept);
        
        return "department/details";
    }
	
	
//	private final DepartmentService deptSvc;
//
	@GetMapping("/list2")
	public void deptList(Model model) {
		log.info("deptList()");
		
		List<Department> list = deptSvc.read();
		
		model.addAttribute("departments", list);
		
	
	}
	
	
	@GetMapping("/details2")
	public void deptDetails(Model model, @RequestParam(name = "id") int id) {
		log.info("deptDetails(id={})",id);
		
		Department dept = deptSvc.read(id);
		model.addAttribute("dept", dept);
		
		List<Employee> deptEmpList = deptSvc.readByIdList(id);
		model.addAttribute("employees", deptEmpList);
	
	}
	
}
