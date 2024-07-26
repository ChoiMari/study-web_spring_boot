package com.itwill.springboot3.dto;

import java.util.List;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.domain.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class DepartmentDetailsDto {
    private Integer departmentId;
    private String departmentName;
    private Employee manager;
    private Location location;
    private List<EmployeeListItemDto> employees;
    
    public static DepartmentDetailsDto fromEntity(Department department, List<Employee> employees) {
    	//Department타입을 DepartmentDetailsDto타입으로 변환하고 List<Employee>타입을 List<EmployeeListItemDto>타입으로 변환해서 DepartmentDetailsDto타입으로 초기화시키는 메서드
        List<EmployeeListItemDto> list = employees.stream()
                .map(EmployeeListItemDto::fromEntity)
                .toList();
        
        return DepartmentDetailsDto.builder()
                .departmentId(department.getId())
                .departmentName(department.getDepartmentName())
                .manager(department.getManager())
                .location(department.getLocation())
                .employees(list)
                .build();
    }
}