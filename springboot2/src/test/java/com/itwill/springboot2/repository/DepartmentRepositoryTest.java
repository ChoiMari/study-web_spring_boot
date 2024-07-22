package com.itwill.springboot2.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot2.domain.Department;
import com.itwill.springboot2.domain.Employee;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
//->원래는 @Test 할 때 마다 주석지우고 각각 테스트 했는데 이번에 이걸 사용해서 test할때마다 주석필요 없음.
// 테스트 클래스 안에 있는 테스트 메서드를 순서대로 지정해서 실행.  OrderAnnotation으로 순서를 지정해서 실행하겠다.
@SpringBootTest
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository deptRepo;
    
    @Test
    @Order(1)
    public void test() {
        // DepartmentRepository 객체를 의존성 주입받았음(객체가 생성됨)을 테스트 
        assertThat(deptRepo).isNotNull();
        log.info("deptRepo: {}", deptRepo);
    }
    
    @Transactional
    @Test
    @Order(2)
    public void findAllTest() {
        // dept 테이블 전체 검색 테스트: 행의 개수가 4개이면 성공.
        List<Department> list = deptRepo.findAll(); //findAll() 전체 검색/전체 행 select
        assertThat(list.size()).isEqualTo(4);//list.size() 행의 갯수. 4개와 같음을 주장.
        
        list.forEach(System.out::println); //@OneToMany(fetch = FetchType.EAGER, mappedBy = "department") 로 해서 ?에 40,30,20,10 4번 실행 됨
    }
    
    @Transactional
    @Test
    @Order(3)
    public void findByTest() {
        // 부서번호(deptno 컬럼, id 필드)로 검색 테스트
        // 부서번호가 테이블에 있는 경우:
        Department dept1 = deptRepo.findById(10).orElseThrow();//orElseThrow()값이 있으면 값을 리턴. 그렇지 않으면 예외던지겠다.get()과 거의 비슷한 메서드
        assertThat(dept1.getId()).isEqualTo(10);
        log.info("dept1: {}", dept1);
        
        //OneToMany관계에서 10번 부서의 모든 직원 정보 출력
        List<Employee> employees = dept1.getEmployees();
        employees.forEach(System.out::println); //-> 원소 개수만큼 출력 됨.
        
        // 부서번호가 테이블에 없는 경우:
        boolean isEmpty = deptRepo.findById(100).isEmpty();//isEmpty() Optional객체가 값을 가지고있는지 없는지. 있으면 true리턴.
        assertThat(isEmpty);
    }

}