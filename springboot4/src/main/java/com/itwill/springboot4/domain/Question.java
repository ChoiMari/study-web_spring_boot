package com.itwill.springboot4.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "QUESTIONS")
public class Question {
	
	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)//create명렁어일때 오라클인 경우에 사용함
	//  mysql은 GenerationType.AUTO 사용
	//-> 오라클 : generated as identity, MySQL : autoincrement //->insert시에 자동 증가 되는 숫자
	//-> JPA는 데이터 베이스에 종속적이지 않음
	// 어떤 DB를 사용하든 엔터티 클래스 그대로 사용가능.
	
	@Column(name = "QID")//엔터티 필드 이름과 테이블 컬럼 이름이 다를때 사용
	private Long id;
	
	@Basic(optional = false) //->테이블 생성시 not null 제약 조건 주는 애너테이션
	private String title;
	
	@Basic(optional = false) //->테이블 생성시 not null 제약 조건 주는 애너테이션
	@Column(length = 1000)//-> 테이블 생성시 varchar2(1000 char)로 만들어짐
	private String content;
	
}
