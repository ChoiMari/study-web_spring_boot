package com.itwill.springboot3.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import com.itwill.springboot3.domain.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class FindByExmapleTest {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	//@Test
	public void test() {
		//Employees테이블에서 firstName이 일치하는 직원 검색
		Employee emp = Employee.builder().firstName("Steven").build(); //->firstName을 제외한 나머지 모든 필드는 null
		log.info("emp = {}",emp);
		//emp = Employee(id=null, firstName=Steven, lastName=null, email=null, phoneNumber=null, hireDate=null, salary=null, commissionPct=null)
		Example<Employee> example = Example.of(emp);
		
		List<Employee> list = empRepo.findAll(example);//동명이인이 있을수 있으니까 findOne()안쓰고 findAll()씀
		//-> 이런 예제를 찾아라.. where e1_0.first_name=? //?에 들어갈 바인딩 파라미터가 Steven이 들어감
		list.forEach(System.out::println);
	}
	
	@Test
	public void test2() {

		//firstName과 lastName이 일치하는 직원 검색
		Employee emp = Employee.builder().firstName("Steven").lastName("Kind").build();
		log.info("emp = {}",emp);
		//emp = Employee(id=null, firstName=Steven, lastName=Kind, email=null, phoneNumber=null, hireDate=null, salary=null, commissionPct=null)
		Example<Employee> example = Example.of(emp);
		
		List<Employee> list = empRepo.findAll(example);//동명이인이 있을수 있으니까 findOne()안쓰고 findAll()씀
		//-> 이런 예제를 찾아라.. where e1_0.last_name=? ande1_0.first_name=? // ?에 들어갈 바인딩 파라미터 Kind,Steven
		//여러개의 조건을 and로 연결한 엔터티를 찾고 싶을때 example객체를 만듬
		//1개만 찾을때는 findOne() ,여러개를 찾을때는 findAll() 사용
		list.forEach(System.out::println);
	}

}
