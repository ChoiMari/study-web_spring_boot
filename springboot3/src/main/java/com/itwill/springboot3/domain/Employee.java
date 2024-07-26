package com.itwill.springboot3.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity //-> 엔터티 클래스
@Table(name = "EMPLOYEES") // EMPLOYEES 테이블에 매핑되는 엔터티 객체. 실제 테이블 이름과 달라서 작성함. 실제 테이블 이름 EMPLOYEES라고 명시
@NoArgsConstructor @Getter @ToString @EqualsAndHashCode //-> @Setter 없음. 그게 권장 사항.

@AllArgsConstructor(access = AccessLevel.PRIVATE)//접근 권한 private
@Builder
public class Employee {

	@Id//-> PK라는 뜻
	@Column(name = "EMPLOYEE_ID") //-> 테이블의 실제 컬럼 이름. 실제 컬럼이름과 달라서 작성함.
	private Integer id; 
	
	//JPA는 카멜 표기법의 엔터티 필드 이름을 테이블 컬럼의 _(언더스코어) 스네이크 표기법의 컬럼이름으로 자동 매핑함.
	//실제 테이블은 first_name인데
	//이걸 JPA가 카멜표기법으로 자동 매핑해서 @Column(name = "FIRST_NAME")이라고 안써도 된다는 것.
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phoneNumber; // 실제 컬럼이름 phone_number
	
	private LocalDate hireDate; //-> hire_date
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_ID")
	private Job job; //job테이블과 관계를 맺고있음.
	
	private Double salary;
	
	private Double commissionPct; //-> 실제 컬럼이름 commission_pct
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_ID")
	private Employee manager;
//	private Integer managerId; //셀프조인
	
	//TODO 
	//private Integer departmentId;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;
	
}
