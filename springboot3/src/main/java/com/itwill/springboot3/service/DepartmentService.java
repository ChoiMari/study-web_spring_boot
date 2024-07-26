package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
	
	private final DepartmentRepository deptRepo;
	
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
	
	public Department read(Integer id) {
		log.info("read(id={})",id);
		
		return deptRepo.findById(id).orElseGet(() -> null);
	}

}
