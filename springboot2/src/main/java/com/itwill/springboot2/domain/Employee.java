package com.itwill.springboot2.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Entity 반드시 지켜야할 2가지 - 기본 생성자 @NoArgsConstructor , - @Id 필드가 반드시 있어야 한다고..
//프라이머리키(PK)에 해당하는 컬럼을 알려주는게 반드시 있어야 한다고..

//ORM(Object Relation Mapping) 를 구현한 것이 JPA. JAP(Java Persistence API)
// 매핑에 대한 스펙을 만든거라고. 자바 영속성 API ->기술을 구현한 자바 규격이 JPA고 
//JAP를 구현한 라이브러리가 Hibernate인데 Hibernate가 ORM의 표준이 되었다고 함. 
//해결
@NoArgsConstructor //-> 기본 생성자 
@Getter @ToString @EqualsAndHashCode
@Entity //-> 데이터 베이스 테이블과 매핑하는 자바 객체. 테이블과 매핑되는 자바 클래스
@Table(name = "EMP") //-> 이건 클래스 이름과 테이블 이름이 다른 경우에 사용하면 된다고 함.
// 만약 클래스 이름이 테이블에 있는 이름인 EMP였다면 문제가 되지 않아서 @Table(name = "EMP")을 안써도 된다고 
public class Employee { //테이블 이름과 다름. 만약 같았으면 @ 1개 생략해도 된다고... 달라서 @ 1개 더 붙인다고 함
	
	@Id //-> Primary key //-> findById 메서드가 자동으로 만들어진다고 함
	@Column(name="EMPNO") //-> 필드 이름과 실제 컬럼 이름이 다를 때 사용. 테이블의 컬럼과 달라서 사용함.
	private Integer id; //사번. 테이블에서는 EMPNO
	
	private String ename; //사원이름
	private String job; 
	
//	@Column(name="MGR") //-> 직원 매니저의 사번
//	private Integer manager; //MGR.  실제 테이블의 컬럼이름과 달라서 @Column(name="MGR")으로 명시함. 같을 경우엔 @컬럼 쓸필요없다.
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MGR")
	private Employee manager;
	
	
	private LocalDate hiredate;
	
	@Column(name = "SAL")
	private Double salary; //급여
	
	@Column(name = "COMM")
	private Double commission; //수당
	
	//private Integer deptno;
	
	
	@ToString.Exclude // 롬복에 있는 것. toString메서드의 출력 문제열에서 제외시킴.// 해주는게 좋다고 함.
	 //이유 : FetchType.LAZY랑 toString과 같이 쓸수 없다고 함. FetchType.EAGER면 문제가 안생기지만 EAGER여도 해주는게 좋다고 함.
	//무얼쓰든 조인 할거면 toString 제외시키라고 함.
	//조인할때는 toString을 제외시키는게 좋다고 .. 안드러면 무한루프 빠질수도 있다고 함.
	// 나중에 필요할때 getter메서드이용하면 된다고.. 안그럼 toString때문에 에러생긴다고 함.
	
//	@ManyToOne(fetch = FetchType.EAGER) //EAGER가 기본값. emp테이블과의 관계.emp을 기준으로 작성 @ManyToOne
	//->fetch = FetchType.EAGER : 검색은 emp테이블에서 검색하는데, 부서테이블의 내용까지도 한꺼번에 검색하겠다. 
	
	@ManyToOne(fetch = FetchType.LAZY)//-> 처음부터 join하지 않고 필요할때 따로 실행하겠다.LAZY쓰는 걸 권장한다고함.(기본값은 EAGER)
	@JoinColumn(name = "DEPTNO") //EMP테이블에서 DEPT테이블과 조인하는 컬럼 이름. 실제 테이블의 컬럼 이름과 달라서 작성함
	private Department department; //컬럼이름이 아니라 엔터티 클래스 이름을 사용.
	
}
