package com.itwill.springboot3.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.dto.EmployeeListItemDto;
import com.itwill.springboot3.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor //생성자에 의한 의존성 주입하려고 선언함
@Slf4j
public class EmployeeService {
	
	private final EmployeeRepository empRepo;
	
	@Transactional(readOnly = true ) //-> 엔터티 객체를 읽기 전용으로 만듬.오로지 select만하고 insert,delete,update 안하겠다. 
	//-> 이렇게 하면 변경 못한다고 함. 이 서비스가 끝날때까지는 엔터티의 변경이 없다.
	public Page<EmployeeListItemDto> read(int pageNo, Sort sort){ //Page<EmployeeListItemDto> 페이징 처리가 되어져있는 객체로 이해하기. 
		//Sort sort 이건 정렬을 염두해놓고 선언한 것.
		log.info("read(pageNo={}, sort={})",pageNo,sort);
		
		//pageable 객체 생성 :  PageRequest.of(페이지번호, 한 페이지에 보여지는 아이템 개수, 정렬기준)
		Pageable pageable = PageRequest.of(pageNo, 10, sort); 
		//1번째 페이지 번호, 2번째 아규먼트값은 페이지당 보여줄 숫자, 정렬기준
		//findAll(Pageable pageable)메서드 호출하면 페이징 처리 끝난 결과 얻을 수 있다고..
		Page<Employee> page = empRepo.findAll(pageable);
		log.info("hasPrevious={}",page.hasPrevious()); //hasPrevious() 이전 페이지가 있으면 true, 없으면 false
		log.info("hasNext={}",page.hasNext()); // page.hasNext() 다음페이지가 있는지 여부 알 수 있는 메서드. 다음 페이지가 있으면 true, 없으면 false
		log.info("getNumber = {}", page.getNumber());//현재 Slice(페이지) 번호
		log.info("getTotalPages={}",page.getTotalPages()); //전체 페이지 개수 //page.getContent() 유용하다고 함
		
		//List<Employee> list =  empRepo.findAll(); //findAll() jpa리파지토리를 상속받으면(인터페이스에서) 자동으로 생기는 메서드.
		
		//log.info("emp list size = {}", list.size()); //실제 테이블에는 107개 행 있어서 107이 로그로 찍혀야 함. list.size() : list개수
		
		
		//Page<Employee> 타입을 Page<EmployeeListItemDto> 타입으로 변환해서 리턴
		return page.map(EmployeeListItemDto::fromEntity);
		
		//-> 람다 표현식 (x) -> EmployeeListItemDto.fromEntity(x)
		//리스트의 원소 1개씩 꺼내서 map통과(Dto타입으로 바꿈) 
	
		
	}
	
	@Transactional(readOnly = true) // 읽기 전용으로 만듬. 기본값은 false여서 바꿈. 오로지 select만하고 insert,delete,update 안하겠다.
	public Employee read(Integer id) {
		log.info("read(id={}",id);
		
		return empRepo.findById(id).orElseGet(()-> null);
	}

}
