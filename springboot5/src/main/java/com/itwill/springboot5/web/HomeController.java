package com.itwill.springboot5.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j //-> 로그 사용 위해 
@Controller//-> 컨트롤러라고 알려줌
public class HomeController {
	
	@GetMapping("/") //-> 컨텍스트 path로 들어오는 겟방식의 요청주소 처리
	public String home() {
		log.info("home()");
		
		return "index"; //-> 뷰의 이름 index.html
	}
	

}
