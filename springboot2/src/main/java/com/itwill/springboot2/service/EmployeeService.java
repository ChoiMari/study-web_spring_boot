package com.itwill.springboot2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot2.domain.Employee;
import com.itwill.springboot2.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {
	
	  // 생성자에 의한 의존성 주입: (1) RequiredArgsConstructor + (2) final field
    private final EmployeeRepository empRepo;
    
    public List<Employee> read() {
        
    	log.info("read()");
        
        // 영속성(저장소) 계층의 메서드를 호출해서 DB 쿼리를 실행.
        return empRepo.findAll();
    }
    
    public Employee read(Integer id) {
       
    	log.info("read(id={})", id);
        
        return empRepo.findById(id).orElseThrow(); //empRepo.findById(id)만 쓰면 Optional<Employee> 타입이라서 그냥 Employee 타입
        //하고 싶으면 메서드 한개 더 쓰면 됨.
        //get(),orElseThrow(),orElseGet(()-> null);값이 없으면 람다표현식에서 리턴해주는 값
    }
	
//	//-> 생성자에 의한 의존성 주입. : (1) @RequiredArgsConstructor  (2) final field
//	private final EmployeeRepository empRepo; 
//	
//	public List<Employee> read(){
//		log.info("read()");
//		
//		//영속성(저장소) 계층의 메서드를 호출해서 DB쿼리를 실행.
//		return empRepo.findAll(); //-> 이미 만들어진 메서드가 있음. 호출만 하면 됨.
//	}
//	
//	public Employee readById(Integer id) {
//		log.info("readById()");
//		
//		return empRepo.findById(id).orElseGet(() -> null);
//	}
    
}
