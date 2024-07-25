package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.dto.EmployeeListItemDto;
import com.itwill.springboot3.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/employee")// 이 컨트롤러의 모든 요청주소는 /employee로 시작해서 매핑함.
public class EmployeeController {
	
	private final EmployeeService empSvc;
	
	@GetMapping("/list")
	public void list(@RequestParam(name = "p", defaultValue = "0") int pageNo, Model model) { //URL에 @RequestParam 없을 경우 0(defaultValue = "0")
		log.info("list(pageNo={})",pageNo);
		
		//서비스(비즈니스) 계층의 메서드를 호출해서 뷰에 전달할 직원 목록을 가져옴.
		Page<EmployeeListItemDto> list = empSvc.read(pageNo,Sort.by("id")); //id기준의 오름차순 정렬(ASC)
		
	//	List<EmployeeListItemDto> list = empSvc.read();
		model.addAttribute("page", list);
	}
	
	@GetMapping("/details/{id}") //@PathVariable에서는 메서드 리턴 타입 void 불가. 안그럼 @PathVariable 준 그 부분의 {id}매핑 주소 바뀔때마다 그에 해당하는 뷰가 있어야함.
	public String details(@PathVariable(name = "id") Integer id, Model model) { //매핑 주소의 {id}읽어 들일수 있어야 하니까 @PathVariable(name = "id") Integer id로 선언함.
		log.info("details(id={})",id);
		
		//서비스(비즈니스) 계층의 메서드를 호출해서 직원 상세 정보를 가져옴.
		Employee emp = empSvc.read(id);
		model.addAttribute("employee", emp);
		
		return "/employee/details"; //@PathVariable쓸때는 String으로 리턴 보내야함.

	}
	
}
