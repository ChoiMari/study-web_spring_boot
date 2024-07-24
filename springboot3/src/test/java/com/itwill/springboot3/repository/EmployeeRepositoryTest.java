package com.itwill.springboot3.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot3.domain.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class EmployeeRepositoryTest {
	
	@Autowired //의존성 주입. Test에서는 생성자이용한 의존성주입 못한다고함
	private EmployeeRepository empRepo;
	
	//주입 제대로 받았는지 확인
	//@Test
	public void testDependencyInjection() {
		//EmployeeRepository 객체를 의존성 주입을 받을 수 있는 지 테스트
		assertThat(empRepo).isNotNull();// empReop가 null아님을 주장. null이 아니면 test 성공.
		
		log.info("empRepo={}",empRepo); 
		//-> empRepo=org.springframework.data.jpa.repository.support.SimpleJpaRepository@65192f16
		
		/*
		 * 상속 관계(중간 생략 있음)
		 * Repository<T,ID>
		 * 	|__CrudRepository<T,ID>, pagingAndSortingRepository<T,ID>
		 * 			|__JpaRepository<T,ID>
		 * 				|__ EmployeeRepository<T,ID>
		 * 					|__SimpleJpaRepository<T,ID>
		 */
	}
	
	//전체 검색 되는지 확인
	//@Test
	public void testFindAll() {
		//employees 테이블 전체 검색 (총 107개 행) test
		long count = empRepo.count();
		assertThat(count).isEqualTo(107L); //count가 107개와 같음을 주장함. test성공이면 count는 107임
		//-> long타입이라서 숫자 끝에 L추가함
		
		List<Employee> list = empRepo.findAll();
		log.info("employees[0]={}",list.get(0)); //-> Employees 테이블의 1번째 행 출력해 봄. 인덱스로는 0번
	}
	
	@Transactional//Lazy로 하면 이거 꼭 있어야 함.
	@Test
	public void testFindById() {
		// EmployeeRepository.findById() 메서드 테스트
		// employees 테이블과 jobs 테이블의 관계 테스트(JOB_ID - JOB_ID)
		// Employees 테이블 Employees테이블 간의 관계 테스트(MANAGER_ID - MANAGER_ID)
		
		//테이블의 id(사번)가 존재하는 경우:
		Employee emp = empRepo.findById(101).orElseThrow();//orElseThrow();값이 없으면 예외던짐.
		log.info("emp={}",emp);
		log.info("emp.job={}",emp.getJob());
		log.info("emp.manager={}",emp.getManager());
		
	}
	

}
