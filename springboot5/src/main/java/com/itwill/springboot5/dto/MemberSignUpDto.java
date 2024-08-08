package com.itwill.springboot5.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.itwill.springboot5.domain.Member;

import ch.qos.logback.core.encoder.Encoder;
import lombok.Data;

@Data
public class MemberSignUpDto { 
	
	private String username;
	private String password;
	private String email;
	//->뷰(HTML) form에서 쓴 input의 name(요청파라미터값)과 같아야 값이 받아짐
	
	public Member toEntity(PasswordEncoder encoder) {
		//dto타입을 entity타입으로 변환 시켜주는 (편의를 위해 만든) 메서드
		return Member.builder()
				.username(username)
				.password(encoder.encode(password))
				.email(email)
				.build();
	}
}
