package com.itwill.springboot3.dto;

import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.domain.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//뷰에서 보여질 일부만 
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeListItemDto {
	private Integer employeeId;
	private String employeeName; //firstName과 lastName을 붙여서 1개의 문자열로 화면에 보여줌.
	private String phoneNumber;
	private String jobTitle;
	private String departmentName; //department가 없는(null) 직원이 있음 그래서 departmentName 호출하는게 문제가 됨.
	//entity.getDepartment()까지 하고 그 다음에 null이냐 아니냐 처리해야함.
	private Department department;
	
	public static EmployeeListItemDto fromEntity(Employee entity) { 
		//dto객체가 만들어지기 전에 메서드를 호출해서 그 메서드가 가지고 있는 정보 리턴해야 되기 때문에 static으로 선언함.
		
		//삼항 연산자 사용
		String deptName = (entity.getDepartment() != null) ? entity.getDepartment().getDepartmentName() : null;
		// null이 아니면 메서드 호출. null이면 그냥 null
		String jobTitle = (entity.getJob() != null) ? entity.getJob().getJobTitle() : null;
		
//		return EmployeeListItemDto.builder()
//				.employeeId(entity.getId())
//				.employeeName(entity.getFirstName() + " " + entity.getLastName())
//				.phoneNumber(entity.getPhoneNumber())
//				.jobTitle(entity.getJob().getJobTitle())
//				.departmentName(entity.getDepartment().getDepartmentName())
//				.build();
		
		//.departmentName(entity.getDepartment().getDepartmentName())부분에 실제 테이블에서 null인 부분이 있어서 예외처리 하려고 수정함.
		//entity.getJob().getJobTitle() 이부분에는 null없지만 위험한 코드라서 수정함.
		return EmployeeListItemDto.builder()
				.employeeId(entity.getId())
				.employeeName(entity.getFirstName() + " " + entity.getLastName())
				.phoneNumber(entity.getPhoneNumber())
				.jobTitle(jobTitle)
				.departmentName(deptName)
				.department(entity.getDepartment())
				.build();
		
	}
}
