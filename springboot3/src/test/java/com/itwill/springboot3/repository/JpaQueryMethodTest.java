package com.itwill.springboot3.repository;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot3.domain.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class JpaQueryMethodTest {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	//@Test
	public void test() {
		List<Employee> list = empRepo.findByDepartmentId(30);
		
		list.forEach(System.out::println);
	} 
	
	//@Test
	public void testSelectFirstName() {
		List<Employee> list;
		list = empRepo.findByFirstName("Steven");
		
		list.forEach(System.out::println);
	} 
	
	//@Test
	public void testSelectFirstNameContaining() {
		List<Employee> list;
		list = empRepo.findByFirstNameContaining("te");
		
		list.forEach(System.out::println);
	} 
	
	//@Test
	public void testSelectFirstNameLike() {
		List<Employee> list;
		list = empRepo.findByFirstNameLike("%te%");
		
		list.forEach(System.out::println);
	}
	
	//@Test
	public void testSelectFirstNameContainingIgnoreCase() {
		List<Employee> list;
		list = empRepo.findByFirstNameContainingIgnoreCase("Te"); //대소문자 구분없이 포함해서 검색함.
		
		list.forEach(System.out::println);
	}
	
	//@Test
	public void testSelectFirstNameContainingIgnoreCaseOrderByFirstNameDesc() {
		List<Employee> list;
		list = empRepo.findByFirstNameContainingIgnoreCaseOrderByFirstNameDesc("TE"); //대소문자 구분없이 포함해서 검색함.
		
		list.forEach(System.out::println);
	}
	
	//@Test
	public void testSelectFindBySalaryGreaterThan() {
		List<Employee> list;
		list = empRepo.findBySalaryGreaterThan(10_000.); //대소문자 구분없이 포함해서 검색함 더블타입이라서 .0붙이는데 그냥 .만 써도 된다고 함.
		
		list.forEach(System.out::println);
	}
	
	//@Test
	public void testSelectFindBySalaryLessThan() {
		List<Employee> list;
		list = empRepo.findBySalaryLessThan(10_000.); //대소문자 구분없이 포함해서 검색함.
		
		list.forEach(System.out::println);
	}
	
	//@Test
	public void testSelectFindBySalaryBetween() {
		List<Employee> list;
		list = empRepo.findBySalaryBetween(10_000.,15_000.);
		//-> 만 이상 만오천이하
		
		list.forEach(System.out::println);
	}
	
	
	//@Test
	public void testSelectFindByHireDateBefore() {
		List<Employee> list;
		list = empRepo.findByHireDateLessThan(LocalDate.of(2003, 1, 1)); //2003, 1, 1 select에 포함 안됨. 
		
		list.forEach(System.out::println);
	}

	//@Test
	public void testSelectFindByHireDateGreaterThan() {
		List<Employee> list;
		list = empRepo.findByHireDateGreaterThan(LocalDate.of(2007, 5, 21)); //대소문자 구분없이 포함해서 검색함.
		
		list.forEach(System.out::println);
	}
	
	@Test
	public void testSelectFindByHireDateBetween() {
		List<Employee> list;
		list = empRepo.findByHireDateBetween(LocalDate.of(2007, 1, 1),LocalDate.of(2007, 12, 31)); //대소문자 구분없이 포함해서 검색함.
		
		list.forEach(System.out::println);
	}
	
	
	

}
