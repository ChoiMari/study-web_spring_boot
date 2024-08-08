package com.itwill.springboot5.dto;

import com.itwill.springboot5.domain.Member;

import lombok.Data;

@Data
public class MemberSignUpDto { 
	
	private String username;
	private String password;
	private String email;
	//->뷰(HTML) form에서 쓴 input의 name(요청파라미터값)과 같아야 값이 받아짐
	
	public Member toEntity() {
		//dto타입을 entity타입으로 변환 시켜주는 (편의를 위해 만든) 메서드
		return Member.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
	}
}
