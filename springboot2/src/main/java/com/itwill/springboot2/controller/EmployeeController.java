package com.itwill.springboot2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot2.domain.Employee;
import com.itwill.springboot2.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor //-> final필드 초기화 하는 생성자 만들어 줌. 생성자에 의한 의존성 주입 구현하기 위해. 
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	//생성자의 의한 의존성 주입. (문서에서의 권장 사항) 1)@RequiredArgsConstructor 2)final 필드
	private final EmployeeService empSvc;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		
		//서비스(비지니스) 계층의 메서드를 호출해서 (데이터베이스의) 직원 목록을 불러옴
		List<Employee> list = empSvc.read();
		
		//직원 목록을 뷰 템플릿에게 전달.
		model.addAttribute("employees", list);
		
	}
	
	@GetMapping("/details")
	public void empDetails(Model model,@RequestParam(name= "id") int id) {
		log.info("details(id={})",id);
		Employee emp = empSvc.readById(id);
		model.addAttribute("emp",emp);
	}

}
