package com.itwill.springboot4.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable//유저에(다른 엔터티 클래스의 필드로)포함 시키고 싶은 경우에 사용하는 애너테이션
//필드로 선언할때 그 필드의 타입으로 사용하기 위해서 . 다른 엔터티에 포함 될수있다.
public class Address {
	private int postalCode;
	private String city;
	private String street;
	private int steetNo;
}
