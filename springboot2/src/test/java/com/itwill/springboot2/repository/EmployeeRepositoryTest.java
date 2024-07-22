package com.itwill.springboot2.repository;

//import static 구문 : static 메서드, 필드 이름을 import
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
	
	
//	 @Test
//	    public void findByTest() {
//	        // 사번이 테이블에 있는 경우:
//	        Optional<Employee> emp1 = empRepo.findById(7788); //사번 7788찾음
//	      //  Employee scott = emp1.get(); 
//	        Employee scott = emp1.orElseGet(() -> null);
//	        assertThat(scott).isNotNull();
//	        assertThat(scott.getEname()).isEqualTo("SCOTT");
//	        log.info("scott: {}", scott);
//	        
//	        // 사번이 테이블에 없는 경우:
//	        Optional<Employee> emp2 = empRepo.findById(1000); //사번이 1000번 행 없음.
//	        Employee none = emp2.orElseGet(() -> null); 
//	        //만약 값이 있음 그 값을 리턴. 
//	        //그렇지 않으면 returns the resultproduced by the supplying function. 
//	        //() -> null 람다 표현식. 값이 없으면 null을 리턴.(값이 없는 경우엔 null리턴)
//	        assertThat(none).isNull();//-> null임을 주장.
//	    }
	
//	@Transactional
//	 @Test
	    public void findByTest() {
	        // 사번이 테이블에 있는 경우:
	        Optional<Employee> emp1 = empRepo.findById(7788); //사번 7788찾음
	      //  Employee scott = emp1.get(); 
	        Employee scott = emp1.orElseGet(() -> null);
	        assertThat(scott).isNotNull();
	        assertThat(scott.getEname()).isEqualTo("SCOTT");
	        log.info("scott: {}", scott);
	        log.info("dept={}",scott.getDepartment());
	        // 사번이 테이블에 없는 경우:
	        Optional<Employee> emp2 = empRepo.findById(1000); //사번이 1000번 행 없음.
	        Employee none = emp2.orElseGet(() -> null); 
	        //만약 값이 있음 그 값을 리턴. 
	        //그렇지 않으면 returns the resultproduced by the supplying function. 
	        //() -> null 람다 표현식. 값이 없으면 null을 리턴.(값이 없는 경우엔 null리턴)
	        assertThat(none).isNull();//-> null임을 주장.
	    }
	    
	    @Transactional
	    @Test
	    public void findMangerTest() {
	    	//사번 7369인 직원 정보 검색 : 
	    	//Optional<Employee> emp	= empRepo.findById(7369); //리턴 타입 Optional<Employee>로 되어있음
	    	//만약 Optional<Employee>쓰기 싫다면?? 그냥 Employee 쓰면 에러. 리턴 타입 맞지 않음. 
	    	//메서드 하나 더 호출해야함 
	    	Employee emp	= empRepo.findById(7369).orElseThrow(); ///->리턴값 없으면 예외던짐. 우리는 이게 지금 null이 아님 알고있어서 사용함.
	    	assertThat(emp.getId()).isEqualTo(7369);
	    	log.info("emp={}",emp);
	    	
	    	Employee mgr = emp.getManager(); //매니져의 매니져도 알아낼수있음 null만 아니면.
	    	assertThat(mgr.getId()).isEqualTo(7902); // 7369직원의 매니저는 7902
	    	log.info("mgr={}",mgr);
	    }
	
	
	
	
//	//@Test
//	public void findByTest() {
//		//TODO 사번으로 검색하는 메서드를 찾아서 단위 테스트 코드 작성(이미 있어서 만들 필요 없음)
//		Optional<Employee> id = empRepo.findById(7369);
//		assertThat(id).isNotNull();
//		log.info("***** id: {}",id);
//	}
//	
//	//TODO DEPT 테이블과 매핑되는 엔터티 클래스를 설계, 리포지토리 인터페이스 작성
//	// 단위 테스트 클래스 작성.
//	//@Test
//	public void testDept() {
//		assertThat(deptRepo).isNotNull();
//		log.info("***** deptRepo : {}",deptRepo); //->deptRepo : org.springframework.data.jpa.repository.support.SimpleJpaRepository@623be2a0
//	}
//	@Test
//	public void findAllTestDept() {
//		List<Department> list = deptRepo.findAll();
//		assertThat(list.size()).isEqualTo(4);
//		for(Department d : list) {
//			System.out.println(d);
//		}
//	}
	
	
}
