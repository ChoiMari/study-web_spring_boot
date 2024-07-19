package com.itwill.springboot2.repository;

//import static 구문 : static 메서드, 필드 이름을 import
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot2.domain.Department;
import com.itwill.springboot2.domain.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class EmployeeRepositoryTest {

	@Autowired //의존성 주입(DI : dependency injection), 제어의 역전(IoC : Inversion of Control). 객체를 생성하는 제어권이 역전되었다..
	private EmployeeRepository empRepo;
	
	@Autowired
	private DepartmentRepository deptRepo;
	
	
	//@Test
	public void test() {
		//Assertions.assertNotNull(empRepo);
		
		
		//import static 구문 : static 메서드, 필드 이름을 import
		//import static org.assertj.core.api.Assertions.assertThat; 하고 사용함. import에 static붙임
		assertThat(empRepo).isNotNull();//empRepo가 null이 아님을 주장하겠다
		//검사할 것을 ()안에서 아규먼트로 넣고 검사.
		//empRepo가 null이 아니면 test 성공
		log.info("***** empRepo: {}",empRepo); //-> info는 toString으로 써야되서 "{}"에 넣어서 이렇게 씀.
	}
	
	//select * from emp
	//@Test
	public void findAllTest() {
		List<Employee> list = empRepo.findAll();
		assertThat(list.size()).isEqualTo(14); // 주장하겠다 list사이즈가 14개라고 
		
		for(Employee e : list) {
			System.out.println(e);
		}
	}
	
	//@Test
	public void findByTest() {
		//TODO 사번으로 검색하는 메서드를 찾아서 단위 테스트 코드 작성(이미 있어서 만들 필요 없음)
		Optional<Employee> id = empRepo.findById(7369);
		assertThat(id).isNotNull();
		log.info("***** id: {}",id);
	}
	
	//TODO DEPT 테이블과 매핑되는 엔터티 클래스를 설계, 리포지토리 인터페이스 작성
	// 단위 테스트 클래스 작성.
	//@Test
	public void testDept() {
		assertThat(deptRepo).isNotNull();
		log.info("***** deptRepo : {}",deptRepo); //->deptRepo : org.springframework.data.jpa.repository.support.SimpleJpaRepository@623be2a0
	}
	@Test
	public void findAllTestDept() {
		List<Department> list = deptRepo.findAll();
		assertThat(list.size()).isEqualTo(4);
		for(Department d : list) {
			System.out.println(d);
		}
	}
	
	
}
