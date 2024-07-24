package com.itwill.springboot3.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot3.domain.Employee;
//첫번째 엔터티 클래스 타입, @Id컬럼 타입 (여기에 int라고 못씀. 제네릭? 그건 기본타입 못쓴다고함. 래퍼?클래스 타입으로만 가능)
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	//JPA query method 작성 방법:
	//JPA에서 약속된 키워드들과 엔터티의 필드 이름들을 사용해서 메서드 이름을 (카멜표기법으로)작성할 때 사용.
	//-> 메서드 이름으로 sql을 추론해서 자동으로 만들어주어 실행해 준다고 함. 그래서 메서드 이름을 잘 작성해야.. 약속된 키워드로...
	
	//select * from employees where department_id = ?
	//결과 행(레코드) 여러개 나올 수 있으면 List (리턴타입 생각해야..)
	//sql문이 select문장인 경우 메서드 이름 findBy
	//from은 신경 쓸 필요 없다고 함.(from employees 이건 어차피 여기서(EmployeeRepository)에서 쓴다고? 그래서 신경안써도 된다고..)
	//where을 by라고 생각
	List<Employee> findByDepartmentId(Integer id); //-> 특별한 키워드를 써서 메서드 이름으로 선언만 함.
	//-> 메서드 이름을 잘못 만들면 원하는 대로 안나온다고 함. 약속된 키워드를 써서 만들어야 한다고...
	//메서드 이름으로 sql문을 추론 한다고 함.
	//개발자가 sql을 작성하는 것이 아니라서 sql튜닝은 못함. 때론 개발자가 직접만드는 sql문 보다 성능이 떨어지는 경우도 있다고 함.
	
	//이름(firstName)으로 검색하기(완전히 일치하는 사람 찾음)
	//select * from employees where first_name = ?
	List<Employee> findByFirstName(String firstName);
	
	//이름에 포함된 단어로 검색하기.(여러글자 대체 %, 1글자 대체 하기 _ 사용)
	//select * from employees where first_name like ?('%?%')
	List<Employee> findByFirstNameContaining(String keyword);
	//-> Containing : 아규먼트에 %를 사용할 필요가 없음.
	
	//select라서 find쓰고 where은 By. 아규먼트는 ?에 들어갈 값 
	List<Employee> findByFirstNameLike(String keyword); 
	//-> Like : 아규먼트로 넣어주는 앞뒤에 % 안 붙여 줌. 따로 수동으로 붙여서 써야함.
	// 아규먼트에 % 또는 _을 사용해야 함.
	
	//이름에 포함된 단어로 검색하는데 단어의 대/소문자 구분 없이 검색하기
	//select * from employees where upper(first_name) like upper(?);
	
	List<Employee> findByFirstNameContainingIgnoreCase(String keyword);
	//find select By where FirstName 필드 이름 Containing 포함하고 있음 IgnoreCase 대소문자 구분 무시??
	
	//이름에 포함된 단어로 검색하는데 단어 대소문자 구분없이 검색하고, 정렬은 이름을 기준으로 내림차순(오름차순이 기본값)
	//select * from employees where upper(first_name) like upper(?) order by first_name desc;
	List<Employee> findByFirstNameContainingIgnoreCaseOrderByFirstNameDesc(String keyword);
	//약속 된 키워드이름으로만 메서드 이름 작성해야!!
	//약속 된 키워드 이름은 https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html 여기서 찾을 수 있음.
	
	//급여가 어떤 값을 초과(GreaterThan)하는 직원들의 정보를 검색하기.
	//select * from employees where salary > ? 
	List<Employee> findBySalaryGreaterThan(Double salary); //int double 못씀.. Integer 또는 Double 사용 (아규먼트로 주는 파라미터 이름 중요하지 않음)
	
	// 급여가 어떤 값 미만(LessThan)인 직원들의 정보
	//where salary < ?
	List<Employee> findBySalaryLessThan(Double salary);
	
	// 급여가 어떤 범위 안(Between)에 있는 직원들의 정보(where salary between ? and ? ) 이상, 이하. >= ? and <= ? 
	List<Employee> findBySalaryBetween(Double start,Double end);
	
	// 입사 날짜가 특정 날짜 이전인 직원들의 정보(where hire_date < ?)
	//List<Employee> findByHireDateBefore(LocalDate date);
	List<Employee> findByHireDateLessThan(LocalDate date);
	
	// 입사 날짜가 특정 날짜 이후인 직원들의 정보(where hire_date > ?)
	//List<Employee> findByHireDateAfter(LocalDate date);
	List<Employee> findByHireDateGreaterThan(LocalDate date);
	
	// 입사 날짜가 날짜 범위 안에 있는 직원들의 정보(where hire_date between ? and ?)
	List<Employee> findByHireDateBetween(LocalDate start, LocalDate end);
	
	

}
