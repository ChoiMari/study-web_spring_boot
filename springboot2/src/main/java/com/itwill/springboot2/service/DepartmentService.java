package com.itwill.springboot2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot2.domain.Department;
import com.itwill.springboot2.domain.Employee;
import com.itwill.springboot2.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class DepartmentService {
	
	
    private final DepartmentRepository deptRepo;
    
    public List<Department> read() {
        
    	log.info("read()");
        
        return deptRepo.findAll();
    }
    
    public Department read(Integer id) {
        
    	log.info("read(id={})", id);
        
        return deptRepo.findById(id).orElseThrow();
    }
	

//	private final DepartmentRepository deptRepo;
//	
//	public List<Department> read(){
//		log.info("read()");
//
//		return deptRepo.findAll();
//	}
//	
//	public Department readById(Integer id) {
//		log.info("readById(id={})",id);
//		Department dept = deptRepo.findById(id).orElseGet(() -> null);
//		
//		 //OneToMany관계에서 10번 부서의 모든 직원 정보 출력
//        List<Employee> employees = dept.getEmployees();
//		
//	//	return deptRepo.findById(id).orElseGet(() -> null);
//        
//        return deptRepo.findById(id).orElseGet(() -> null);	
//		
//	}
//	
	public List<Employee> readByIdList(Integer id) {
		log.info("readByIdList(id={})",id);
		
		Department dept = deptRepo.findById(id).orElseGet(() -> null);
		
		 //OneToMany관계에서 10번 부서의 모든 직원 정보 출력
        List<Employee> employees = dept.getEmployees();
		
	//	return deptRepo.findById(id).orElseGet(() -> null);
        
        return employees;
	}
	
}
