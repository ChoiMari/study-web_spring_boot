package com.itwill.springboot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot2.domain.Department;
//<>에 들어가는 값 : 엔터티 클래스 이름, 그 엔터티 클래스의 @Id컬럼의 타입
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
