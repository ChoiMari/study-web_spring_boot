package com.itwill.springboot2.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Entity 반드시 지켜야할 2가지 - 기본 생성자 @NoArgsConstructor , - @Id 필드가 반드시 있어야 한다고..
//프라이머리키(PK)에 해당하는 컬럼을 알려주는게 반드시 있어야 한다고..

//ORM(Object Relation Mapping) 를 구현한 것이 JPA. JAP(Java Persistence API)
// 매핑에 대한 스펙을 만든거라고. 자바 영속성 API ->기술을 구현한 자바 규격이 JPA고 
//JAP를 구현한 라이브러리가 Hibernate인데 Hibernate가 ORM의 표준이 되었다고 함. 
//해결
@NoArgsConstructor //-> 기본 생성자 
@Getter @ToString @EqualsAndHashCode
@Entity //-> 데이터 베이스 테이블과 매핑하는 자바 객체. 테이블과 매핑되는 자바 클래스
@Table(name = "EMP") //-> 이건 클래스 이름과 테이블 이름이 다른 경우에 사용하면 된다고 함.
// 만약 클래스 이름이 테이블에 있는 이름인 EMP였다면 문제가 되지 않아서 @Table(name = "EMP")을 안써도 된다고 
public class Employee { //테이블 이름과 다름. 만약 같았으면 @ 1개 생략해도 된다고... 달라서 @ 1개 더 붙인다고 함
	
	@Id //-> Primary key
	@Column(name="EMPNO") //-> 필드 이름과 실제 컬럼 이름이 다를 때 사용. 테이블의 컬럼과 달라서 사용함.
	private Integer id; //사번
	
	private String ename; //사원이름
	private String job; 
	
	@Column(name="MGR") 
	private Integer manager; //MGR
	
	private LocalDate hiredate;
	
	@Column(name = "SAL")
	private Double salary;
	
	@Column(name = "COMM")
	private Double commission;
	
	private Integer deptno;
	
	
}
