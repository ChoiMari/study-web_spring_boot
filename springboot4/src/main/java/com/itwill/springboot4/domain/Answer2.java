package com.itwill.springboot4.domain;

import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ANSWERS2") //-> 실제 테이블 이름 ANSWERS2로 설정. 엔터티 클래스이름과 실제 DB테이블의 이름이 다를때 사용함.
public class Answer2 {
	@Id //PK 컬럼을 의미 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //-> 테이블에서 insert시 자동으로 증가하는 컬럼
	private Long id;
	
	@Basic(optional = false)//-> not null 제약 조건을 갖는 컬럼으로 테이블 생성함.
	private String answers; //-> 질문 1개가 답변 여러개를 갖음.(답변 기준으로)
	
	
}
