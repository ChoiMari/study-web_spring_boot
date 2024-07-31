package com.itwill.springboot5.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// thymeleaf 3.x 버전~ 부터 ServletUriComponentsBuilder 클래스 타입을 사용할 수 없어서 만든 클래스.
//사용할 수 있었으면 그냥 바로 ServletUriComponentsBuilder.fromCurrentRequest(); 해버렸으면 되었지만..
public class ServletUriComponentsBuilderWrapper {
	
	public static ServletUriComponentsBuilder fromCurrentRequest() {
		
		return ServletUriComponentsBuilder.fromCurrentRequest();
	}

}
