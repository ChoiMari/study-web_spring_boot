package com.itwill.springboot3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot3.domain.Employee;
//첫번째 엔터티 클래스 타입, @Id컬럼 타입 (여기에 int라고 못씀. 제네릭? 그건 기본타입 못쓴다고함. 래퍼?클래스 타입으로만 가능)
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
