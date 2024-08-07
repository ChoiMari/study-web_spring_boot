package com.itwill.springboot5.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
	
	@GetMapping("/signin") //-> 선생님은 요청주소 만드실 때 전부 소문자를 사용하신다고 함.
	//(리눅스는 대소문자 구분. 윈도우는 과거에 구분하지 않았다고.. 
	//그래서 요청주소에 대소문자 섞어서 쓰는 경우가 있으면 리눅스와 윈도우가 다른 결과를 주는 경우가 있어서 그러시다고 함)
	//-> SecurityConfig.securityFilterChain 메서드안에서 호출한 formLogin()메서드에서 아규먼트로 설정한 요청 주소.
	public void signIn() {
		log.info("GET signIn()");
	}
	
	
//	@PostMapping("/signin")
//	public void signIn() {
//		log.info("POST signIn()");
//		
//	} => 만들 필요 없음.
	
}
