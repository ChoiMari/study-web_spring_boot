package com.itwill.springboot5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
//->스프링 컨테이너에서 생성하고 관리하는 설정 컴포넌트 라는 뜻. 
//-> 스프링부트 애플리케이션이 시작 될 때 생성됨.(@서비스 @컨트롤러 라고 붙이는거랑 비슷하다고)
//-> 스프링 컨테이너에서 필요한 곳에 의존성 주입을 해줌.
//참고 bin클래스에서 붙일수있는 @애너테이션들 :
//@Component @Controller @RestController @Service @Repository @Configuration 
//-> 용도에 맞게 사용하면 된다고 함.
public class SecurityConfig {

	//Spring Security 5 버전부터 비밀번호는 반드시 암호화를 해야만 한다고 함
	//-> 만약 비밀번호를 암호화 하지 않으면 HTTP 403(access denied 접근거부)에러 또는
	//-> HTTP 500( 내부 서버 오류 internal server error) 에러가 발생함.
	//-> 비밀 번호를 암호화 하는 객체를 스프링 컨테이너가 bean으로 관리해야 한다고 함.
	//-> 어떤 암호화 알고리즘을 사용하는지 스프링 컨테이너한테 알려주어야 한다고
	@Bean //->xml의 <Bean>이 자바코드로 된거라고 생각하면 된다고 함.
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //->암호화 알고리즘 중의 하나.
		//=> 다형성 리턴타입은 PasswordEncoder. 상위 타입 리턴하는건 BCryptPasswordEncoder 하위타입
	}//-> 비밀번호 암호화 하려고 씀
	
	
	@Bean
	public UserDetailsService inMemoryUserDetailsService() {
		//UserDetailsService -> 로그인, 로그아웃 등의 회원가입을 도와주는 서비스 인터페이스.
		//-> 이걸 구현하는 클래스와 메서드가 반드시 있어야 한다고 함.
		//-> 지금은 test용으로 만드는 거라고(원리를 배우려고)
		
		//(부트)애플리케이션이 동작 중에 메모리에 임시 저장하는 사용자 객체를 생성함.
		//(애플리 케이션이 종료 되면 사용자가 없어지고 실행되면 생겨나는 사용자 객체)
		UserDetails user1 = User.withUsername("user1") //withUsername("user1") : 로그인 사용자 아이디
				.password(passwordEncoder().encode("1111")) //.password("1111")이렇게는 안됨. 비밀번호 암호화 해서 아규먼트로 넣어야함
				//->암호화 시킨 로그인 비밀번호(비밀번호 입력창에는 1111로입력할건데 암호화되어서 비교하게 된다고 함)
				.roles("USER") //-> 사용자 권한(ADMIN:관리자 권한, USER: 일반 사용자 권한, ...등). 선택 사항(권한을 줘두되고 안줘도 된다고).
				.build(); //-> USER 객체를 생성
		//UserDetails는 인터 페이스이고 User 는 클래스 => 다형성
		//우리가 (멤버)엔터티를 만들면 UserDetails를 구현하고 있어야한다는 뜻.
		//User(클래스)가 가지고 있는 필드들을 (build패턴으로) 초기화해서 생성한다 라고 이해하면 된다고 함
		
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
	
	//->UserDetailsService:  사용자 관리(로그인, 로그아웃, 회원가입 등) 을 위한 서비스 인터 페이스
	//-> 스프링 부트 애플리케이션에서 스프링 시큐리티를 이용한 로그인, 로그아웃을 하려면
	// 반드시 UserDetailsService(인터페이스)를 구현하는 서비스 클래스와 
	//UserDetails(인터페이스)를 구현하는 엔터티 클래스가 있어야함
	//이 코드를 만드는 목적 : 사용자 엔터티와 사용자 서비스를 구현하기 전에 테스트 용도로 사용할 코드.
	//-> 나중에는 필요가 없다고 함. test용도
	//-> 스프링 부트가 (콘솔로 확인 가능한)자동으로 만들어주는 비밀번호 이외에 비밀번호를 만들어서 사용하려고 만드는 코드라고 함.

}
