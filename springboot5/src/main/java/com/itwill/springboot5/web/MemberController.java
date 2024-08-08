package com.itwill.springboot5.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot5.domain.Member;
import com.itwill.springboot5.dto.MemberSignUpDto;
import com.itwill.springboot5.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberSvc;
	
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
	
	@GetMapping("/signup")
	public void signUp() {
		log.info("GET signUp");
	}
	
	@PostMapping("/signup")
	public String signUp(MemberSignUpDto dto) { 
		//-> 오버로딩 : 파라미터 선언이 다르면 메서드 이름이 같아도 다른 메서드로 인식함
		log.info("POST signup(dto={})",dto);
		//TODO : 서비스 계층의 메서드를 호출해서 회원가입 정보들을 DB에 저장(insert)해야함
		Member member = memberSvc.create(dto);
		log.info("회원가입 member : {}",member);
		
		//회원가입에 성공하면 로그인 페이지로 이동(리다이렉트)
		return "redirect:/member/signin";
		//-> 리다이렉트 : 로그인 [또는 자동로그인 시켜놓고(홈)] 페이지로 이동...
	}
	
}
