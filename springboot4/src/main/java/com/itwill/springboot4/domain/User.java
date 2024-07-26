package com.itwill.springboot4.domain;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity//-> 엔터티 클래스
@Table(name = "USERS") //-> 엔터티 클래스와 테이블이름이 다를때 사용 테이블 이름 USERS
public class User {
	
	@Id //-> 테이블의 PK라는 뜻
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //-> 테이블 생성시 자동 증가하는 용도 
	private Long id;
	
	@NaturalId//pk이와 구분하기 위해서 사용하는 애너테이션. 유니크 제약조건. 테이블에서 유니크(중복X)값을 갖는 컬럼인 경우에 사용가능
	//-> JPA에서 테이블을 자동 생성할때 사용하는 것이라고 함
	@Basic(optional = false)//(기본값은 true) not null제약조건이라는 뜻.
	private String username; //-> 회원가입시 id라고 생각하면 됨. 위의 id필드는 자동증가하는 번호 pk
	
	@Basic(optional = false) //-> not null제약조건 주는 애너테이션
	private String password;
	
	
	//Enum타입 이용 -> check조건 적용
	//ORDINAL(기본값) 외에 STRING도 사용가능
	//@Enumerated(EnumType.ORDINAL)//-> 안써도 상관없다고 EnumType.ORDINAL쓰면 check (gender between0 and2)로 테이블이 만들어진다
	//enum에 있는 그 범위안에서만 insert할수있다고 check 제약 조건이 자동으로 만들어짐  
	//gender number(3,0) check (gender between0 and2)
	//FEMALE -> 0, MALE -> 1, UNDEFINED -> 2
	
	@Enumerated(EnumType.STRING) //-> 상수열(enum에 선언한)을 문자열로 저장하는 컬럼
	//-> gendervarchar2(255 char) check (gender in('FEMALE','MALE','UNDEFINED'))로 테이블이 만들어진다.
	private Gender gender;
	
	@Column(length = 1000) //-> meno varchar2(1000 char)로 테이블이 만들어짐
	private String meno;
	
	@Embedded //-> @Embeddable와 반대. @Embeddable로 선언된 객체를 포함할 때 사용함. 생략 가능하다
	private Address address;
	
}
