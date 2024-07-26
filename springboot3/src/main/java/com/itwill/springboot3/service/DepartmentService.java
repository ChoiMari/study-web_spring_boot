package com.itwill.springboot3.service;



import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.dto.DepartmentDetailsDto;
import com.itwill.springboot3.repository.DepartmentRepository;
import com.itwill.springboot3.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
	
	private final DepartmentRepository deptRepo;
	private final EmployeeRepository empRepo; //-> 생성자에 의한 의존성 주입
	
	@Transactional(readOnly = true)//insert,update,delete가 일어나지 않을때(읽기전용)으로 만들어서 명시해주는게 좋다고 함.
	public Page<Department> read(int pageNo, Sort sort){
		log.info("read(pageNo={},sort={})", pageNo, sort);
		
		Pageable pageable = PageRequest.of(pageNo, 5, sort);//페이지 번호, 페이지당 보여줄 개수(페이지당 5개씩보겠다라고 설정), 정렬
		Page<Department> page = deptRepo.findAll(pageable);
		log.info("hasPrevious={}",page.hasPrevious()); //hasPrevious() 이전 페이지가 있으면 true, 없으면 false
		log.info("hasNext={}",page.hasNext()); // page.hasNext() 다음페이지가 있는지 여부 알 수 있는 메서드. 다음 페이지가 있으면 true, 없으면 false
		log.info("getNumber = {}", page.getNumber());//현재 Slice(페이지) 번호
		log.info("getTotalPages={}",page.getTotalPages()); //전체 페이지 개수 //page.getContent() 유용하다고 함		log.info(null);
		//return deptRepo.findAll(); 
		return page;
	}
	
//	public Department read(Integer id) {
//		log.info("read(id={})",id);
//		
//		return deptRepo.findById(id).orElseGet(() -> null);
//	}
	
	  @Transactional(readOnly = true)
	    public DepartmentDetailsDto read(Integer id) {
	        log.info("read(id={})", id);
	        
	        Department department = deptRepo.findById(id).orElseThrow();
	        List<Employee> employees = empRepo.findByDepartmentId(id);
	        log.info("# of employees = {}", employees.size());
	        
	        return DepartmentDetailsDto.fromEntity(department, employees);
	    }

	  @Transactional(readOnly = true) //insert,update,delete를 하지 않을 경우 읽기전용으로 만듬
	  public DepartmentDetailsDto read(String departmentName) {
		  log.info("read(departmentName={})",departmentName);
		  
		  //부서이름으로 검색하기 서비스
		  //Department 엔터티 객체 생성:(객체 생성하려고 Department에 빌더만듬)
		  //검색 조건이 될 엔터티 객체를 생성하고 객체의 특정 필드를 설정
		  Department department = Department.builder().departmentName(departmentName).build();
		  //-> 나머지 필드 전부 null이고 departmentName만 초기화 됨.
		  
		  //Example 객체 생성(부서예제)
		  Example<Department> example = Example.of(department); //-> 부서 예제인데 이름을 가지고 있는 부서다 라는 뜻.
		  //주어진 엔터티 객체의 필드 값들을 기반으로 하여 검색 조건을 정의함
		  //Example.of(department)는 기본적으로 정확히 일치하는 조건으로 검색함.
		  
		  // Example 객체를 사용하여 deptRepo레포지토리에서 검색
		  //Example객체를 findOne() : 레코드 1개를 찾겠다 또는 findAll() : 레코드 여러개를 찾겠다 메서드의 아규먼트로 전달:
		  //같은 이름의 부서 1개(유니크면) findOne()메서드 쓰면 된다고
		  Department resultDept = deptRepo.findOne(example).orElseThrow(); 
		  //->deptRepo.findOne(example)까지하면 값이 있을수도 있고 없을 수도 있으니까 그런 객체의 리턴타입 Optional<Department> 
		  //findOne()이라서 fetch first ? rows only
		  // select * from departments where department_name = ? fetch first ? rows only 실행됨. (부서이름으로 검색해서 부서정보가져오기)
		  
		  log.info("resultDept id={}", resultDept.getId()); //부서번호(부서의id가 로그로 출력됨)
		  
		  List<Employee> employees = empRepo.findByDepartmentId(resultDept.getId());
		  ////select * from employees where department_id = ? 를 실행하는 메서드(부서번호로 검색)
		  //부서 번호로 검색해서 해당 부서에서 근무하는 직원정보 가져오기
		  
		  return DepartmentDetailsDto.fromEntity(resultDept, employees); //DepartmentDetailsDto타입으로 변환해서 리턴함.
		  
		  //example 객체를 사용했을때 방법 보여줄려고 만든 것. 꼭 이런 방법만 있는건 아니라고..
		  //다른 방법도 사용가능
	  }
}
