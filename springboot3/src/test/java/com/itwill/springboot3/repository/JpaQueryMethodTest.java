package com.itwill.springboot3.repository;

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
	
	@Test
	public void testSelectFirstNameContainingIgnoreCaseOrderByFirstNameDesc() {
		List<Employee> list;
		list = empRepo.findByFirstNameContainingIgnoreCaseOrderByFirstNameDesc("TE"); //대소문자 구분없이 포함해서 검색함.
		
		list.forEach(System.out::println);
	}
	

}
