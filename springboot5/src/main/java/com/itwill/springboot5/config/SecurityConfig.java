package com.itwill.springboot5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
//->스프링 컨테이너에서 생성하고 관리하는 설정 컴포넌트 라는 뜻. 
//-> 스프링부트 애플리케이션이 시작 될 때 생성됨.(@서비스 @컨트롤러 라고 붙이는거랑 비슷하다고)
//-> 스프링 컨테이너에서 필요한 곳에 의존성 주입을 해줌.
//참고 bin클래스에서 붙일수있는 @애너테이션들 :
//@Component @Controller @RestController @Service @Repository @Configuration 
//-> 용도에 맞게 사용하면 된다고 함.

@EnableMethodSecurity  //-> 각각의 컨트롤러 메서드에서 인증(로그인), 권한 설정을 하기 위해서 사용하는 애너테이션
public class SecurityConfig {

	//Spring Security 5 버전부터 비밀번호는 반드시 암호화를 해야만 한다고 함
	//-> 만약 비밀번호를 암호화 하지 않으면 HTTP 403(access denied 접근거부)에러 또는
	//-> HTTP 500( 내부 서버 오류 internal server error) 에러가 발생함.
	//-> 비밀 번호를 암호화 하는 객체를 스프링 컨테이너가 bean으로 관리해야 한다고 함.
	//-> 어떤 암호화 알고리즘을 사용하는지 스프링 컨테이너한테 알려주어야 한다고
	@Bean //->xml의 <Bean>이 자바코드로 된거라고 생각하면 된다고 함.
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //->암호화 알고리즘 중의 하나.
		//=> 다형성 리턴타입은 PasswordEncoder. 상위 타입 리턴하는건 BCryptPasswordEncoder 하위타입
	}//-> 비밀번호 암호화 하려고 씀
	
	/*
	@Bean
	UserDetailsService inMemoryUserDetailsService() {
		//UserDetailsService -> 로그인, 로그아웃 등의 회원가입을 도와주는 서비스 인터페이스.
		//-> 이걸 구현하는 클래스와 메서드가 반드시 있어야 한다고 함.
		//-> 지금은 test용으로 만드는 거라고(원리를 배우려고)
		
		//(부트)애플리케이션이 동작 중에 메모리(heap)에 임시 저장하는 사용자 객체를 생성함.
		//(애플리케이션이 종료 되면 사용자가 없어지고 실행되면 생겨나는 사용자 객체)
		UserDetails user1 = User.withUsername("user1") //withUsername("user1") : 로그인 사용자 아이디
				.password(passwordEncoder().encode("1111")) //.password("1111")이렇게는 안됨. 비밀번호 암호화 해서 아규먼트로 넣어야함
				//->암호화 시킨 로그인 비밀번호(비밀번호 입력창에는 1111로입력할건데 암호화되어서 비교하게 된다고 함)
				.roles("USER") //-> 사용자 권한(ADMIN:관리자 권한, USER: 일반 사용자 권한, ...등). 선택 사항(권한을 줘두되고 안줘도 된다고).
				.build(); //-> USER 객체를 생성
		//UserDetails는 인터 페이스이고 User 는 클래스 => 다형성
		//우리가 (멤버)엔터티를 만들면 UserDetails를 구현하고 있어야한다는 뜻.
		//User(클래스)가 가지고 있는 필드들을 (build패턴으로) 초기화해서 생성한다 라고 이해하면 된다고 함
		//User : 스프링에서 기본 제공하는 User라고 함. 우리는 직접만든다고..
		UserDetails user2 = User.withUsername("user2")
				.password(passwordEncoder().encode("2222"))
				.roles("ADMIN","USER") //-> 아규먼트 가변길이로 줄수있음. 권한 관리자와 일반 사용자 권한 2가지 줌
				.build();
		
		UserDetails user3 = User.withUsername("user3")
				.password(passwordEncoder().encode("3333"))
				.roles("ADMIN") //-> 관리자 권한만 줌
				.build();
		
		return new InMemoryUserDetailsManager(user1, user2, user3); //-> 아규먼트 InMemoryUserDetailsManage 가변길이 인수
		//-> User 타입 객체 3개를 가지고 있는 UserDetailsService 객체를 생성하고 리턴함.
		
	}
	*/
	//->UserDetailsService:  사용자 관리(로그인, 로그아웃, 회원가입 등) 을 위한 서비스 인터 페이스
	//-> 스프링 부트 애플리케이션에서 스프링 시큐리티를 이용한 로그인, 로그아웃을 하려면
	// 반드시 UserDetailsService(인터페이스)를 구현하는 서비스 클래스와 
	//UserDetails(인터페이스)를 구현하는 엔터티 클래스가 있어야함
	//이 코드를 만드는 목적 : 사용자 엔터티와 사용자 서비스를 구현하기 전에 테스트 용도로 사용할 코드.
	//-> 나중에는 필요가 없다고 함. test용도
	//-> 스프링 부트가 (콘솔로 확인 가능한)자동으로 만들어주는 비밀번호 이외에 비밀번호를 만들어서 사용하려고 만드는 코드라고 함.
	
	
	// 스프링 시큐리티 필터 체인 객체(bean)생성
	// 필터 체인의 역할 : 로그인과 로그아웃과 관련된 인증 필터에서 필요한 설정을 세팅(구성)하는 것.
	//-> test용 아님. 계속 이용하는 코드
	//-> 필터에서 필요한 설정이란? 
	// - 로그인 페이지(뷰) / 로그아웃 페이지 설정
	// - 페이지 접근 권한(ADMIN, USER) 설정
	// - 인증 설정(로그인 없이 접근 가능한 페이지 / 로그인 해야만 접근 가능한 페이지)
	//-> 로그인여부와 상관없이 권한 여부만으로도 접근여부를 따질수도 있다고 함.
	
	@Bean //-> 기본 필터체인은 이제 무시가 됨.
	//(기본적으로 적용되는 필터체인은 로그인해야 모든 페이지에 접근이 가능했는데 이제 무시가 되고)지금 우리가 만든 필터 체인이 적용됨. 
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{//public 수식어는 뺌
	//리턴 타입 SecurityFilterChain를 구현하는 클래스 객체를 리턴해주어야함
		
		// 시큐리티 관련 설정들을 구성
		// CSRF(Cross Site Request Forgery) 기능을 비활성화 : (활성화시키고 쓰는게 좋다고 함. 보안유지하려면 활성화시키는게 좋다고)
		//-> 해킹 관련 기술이라고 함.
		//로그인할때 해킹 방지하기 위해서 숨기는 input필드 만들어서 거기다 보안키(인증키) 넣는거라고..(개발자도구에선 볼수있다고)
		//보안키 일치 비교(가짜 사이트에서 온건지 아닌지 확인함) 기본적으로는 활성화되어져 있다고함. 특별히 설정하지 않아도
		// 자동으로 생긴다고. 
		//문제는 우리가 Ajax쓰는게 있는데(댓글) 거기에도 CSRF 토큰이 필요하다고 함. CSRF토큰 이용해서 그것과 함께 Ajax요청을 보내야 하는데
		// 우리는 그걸 고려하지 않고 댓글기능을 구현해놓았기때문에 지금 비활성화 시키는 거라고함.
		// 프로젝트 구현에는 Ajax에서 CSRF를 어떻게 구현할지 생각해 보라고 함. 비활성시키는건 안좋은거라고(보완에)
		
		// 만약, CSRF 기능을 활성화한 경우에는,
		//Ajax POST/PUT/DELETE(정보 전달받는거 클라이언트쪽에서 서버로 받는 정보가 있는) 요청에서 CSRF 토큰을 서버로 전송하지 않으면
		// HTTP 403 에러가 발생함 -> 그래서 비활성화 한다는것(사실은 활성화하는게 더 보안에 좋지만, 우리가 만든 댓글기능은 이걸 고려하지 않고 구현했기 때문)
		http.csrf((csrf)->csrf.disable()); //->아규먼트. 람다 표현식으로 넣어야 한다고 함 //-> CSRF 비활성화 시킴
		
		//로그인 페이지(폼) 설정 - 스프링 시큐리티에서 제공하는 기본 HTML 페이지를 사용하겠다 설정
		//(기본적으로 제공되는 로그인페이지 사용하겠다 설정)
		//http.formLogin(Customizer.withDefaults());//-> import할때 곧 없어질 메서드 사용하지 말라고 함
		//** 우리가 만드는 Custom 로그인 페이지(HTML)를 사용하도록 바꿈(기본제공 로그인 페이지가 아니라 우리가 만든 로그인 페이지 사용)
		http.formLogin((login) -> login.loginPage("/member/signin")); //-> 람다 표현식 1문장만 쓸 경우에는 {}생략 가능
		//아큐먼트로 넣은 요청주소/member/signin는 컨트롤러 이름. /member/signin 페이지로 간다는 뜻.
		//인증이 필요한 페이지를 /member/signin로 리다이렉트 하겠다는 뜻.
		
		//** 페이지 접근 권한, 인증 구성 : 아래의 1 또는 2 방법 중 한 가지를 선택해서 사용하면 됨.
		// 1. HttpSecurity.authorizeHttpRequests(Customizer customizer) 메서드에서 설정
		// -> 장점 : 한곳에서 모든 설정을 구성할 수 있음.
		//- > 단점 :  새로운 요청 경로가 생길때 마다 이 파일을 찾아서 설정 구성 코드를 수정해야 한다.
		// 2. 컨트롤러 메서드에서 애너테이션으로 설정
		// (1) SecurityConfig 빈에 @EnableMethodSecurity 애너테이션을 설정하고,
		// (2) 각각 컨트롤러 메서드에서 @PreAuthorize 또는 @PostAuthorize 애너테이션으로 설정함.
	/*	http.authorizeHttpRequests((auth)-> 
		//		auth.anyRequest().authenticated());// 모든 요청주소anyRequest()는 전부 다 authenticated() 인증된 사용자만 가능하다라고 설정.
		// 인증관련 메서드들을 가지고 있는 객체가 람다표현식으로 쓴 변수에 전달이 된다고 그래서 그 객체. 으로 메서드 호출해서 설정하면 된다고함
		//auth.anyRequest().authenticated() :  모든 요청 주소에 대해서 role(권한)에 상관 없이 아이디/비밀번호 인증(로그인)을 하는 경우에만 접근 가능하게 설정
		//role(권한)과 상관없이 로그인이 되어야지 페이지 접근가능하게 설정함
		
		//모든 요청 주소에 대해서 "USER" 권한을 가진 아이디/비밀번호 인증을 하는 경우만 접근 가능하도록 설정함.
		//		auth.anyRequest().hasRole("USER"));
		//아이디와 패스워드가 일치 하더라도(로그인하더라도) 권한 별로 접근 가능하게 설정(권한이 없으면 페이지 접근X)
		//USER로 준 권한만 로그인 가능하게 설정함. 그래서 user1,user2만 로그인 가능함
		
		
		//로그인이 필요한 페이지와 그렇지 않은 페이지를 구분해서 접근 가능하도록 설정을 구성함.
		auth.requestMatchers("/post/create","/post/details","/post/modify",
				"/post/delete","/post/update","/api/comment/**") //** (해당 요청주소의 하위주소까지 포함해서)모든이라는 뜻(pathvariable을 **로 처리함)
		//-> 로그인이 필요한 요청 주소들을 나열하면 된다고 함. 아규먼트로 넣은 요청주소는
		//.authenticated() //->로그인 인증된 사용자만 해당 요청을 처리할 수 있도록 설정. 권한은 상관 없다(아이디랑 패스워드만 확인)
		//.authenticated()대신에 .hasAnyRole("권한")를 사용하면 아이디,패스워드 +(아규먼트로 넣은)권한까지 확인해서 접근권한 설정
		// 메서드 호출 순서 중요하다고 함.
		.hasRole("USER")
		//이 코드에서 추가로 .requestMatchers("요청주소").hasRole("권한") 더 할 수 있다고 함
		.anyRequest() //애플리케이션으로 들어오는 모든 HTTP 요청을 의미
		.permitAll() //requestMatchers("/post/create","/post/details","/post/modify",
		//"/post/delete","/post/update","/api/comment/*")에 아규먼트로 넣은 요청주소가 아니면
		//해당 요청을 인증이나 인가 없이 모두 허용하겠다는 의미
		);
		
		*/
		return http.build(); //->리턴 타입 DefaultSecurityFilterChain(하위클래스) : SecurityFilterChain가 상위(수퍼클래스) 타입
	}
}