package com.itwill.springboot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot2.domain.Employee;


/*
class A extends B{}
class C implements D {}
interface I extends J {} //-> 가능
*/
//인터페이스는 인터페이스를 상속?? --> 가능함
//클래스는 다중상속 불가(2개이상 상속 불가능함)
//T는 엔터티의 타입, 2번째는 @Id타입(PK를 의미)
//엔터티 -> 어떤 테이블(클래스)에서

/*
 * 상속 관계
 * Repository<T, ID>
 * |__ CrudRepository<T, ID>, PagingAndSortingRepository<T, ID> //-> 페이징+정렬 메서드 있다고 함
 *     |__ JpaRepository<T, ID> 
 * 
 * T: Entity 클래스, ID: Entity 클래스의 @Id 필드 타입
 */

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
//-> 이것만 있으면 select,insert,update,delete 다 된다고 함
