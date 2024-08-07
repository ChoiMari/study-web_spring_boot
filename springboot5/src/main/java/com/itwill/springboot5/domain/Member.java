package com.itwill.springboot5.domain;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder
@Getter @ToString(callSuper = true) //->(callSuper = true) : toString을 만들때 수퍼클래스도 같이 만듬으로 설정함
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//-> callSuper : 부모클래스의 필드도 같이 비교하겠다 
// exclude : 비교에서 제외할 필드 설정(배열로 만들어서) 사용 예)@EqualsAndHashCode(exclude = {"password","email"}) 비밀번호와 이메일필드는 비교대상에서 제외함
//@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false) : 명시적으로 포함 시킨 것만 비교 하겠다고 설정 하고 수퍼(부모)클래스도 포함 안하겠다(false)고 설정
//onlyExplicitlyIncluded 속성 : @EqualsAndHashCode.Include 애너테이션이 설정된 필드만 사용할 것인지 여부. true값주면 그렇게 사용하겠다. false 그렇게 사용안하겠다
//callSuper 속성 : 수퍼(부모)클래스의 equals(),hashCode() 메서드를 사용할 것인지 여부. - 사용할 것이면 true, 사용 X false
@Entity
@Table(name = "MEMBERS")
public class Member extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@EqualsAndHashCode.Include // username 필드를 equals()와 hashCode()를 재정의 할 때 사용. (단 클래스 앞에 @EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)를 썼을때 적용
	@NaturalId //-> unique 제약조건
	@Basic(optional = false) //-> not null 제약조건
	@Column(updatable = false) //->update쿼리에서 set절에서 제외하기 위해서 사용함. 변경 불가능(update 불가로 설정함)
	private String username;
	
	@Basic(optional = false)
	private String password;
	
	@Basic(optional = false)
	private String email;
	
	@Builder.Default //-> 빌드 패턴에서도 null이 아닌 HashSet<>객체로 초기화 될 수 있도록 하려고 사용함.
	//빌드 패턴에서도  = new HashSet<>();이 되게 하려고 사용함
	@ToString.Exclude //-> toString()에서 제외시킴
	@ElementCollection(fetch = FetchType.LAZY) //-> 연관 테이블 사용(Member_roles 테이블 사용)하기 위해서.
	@Enumerated(EnumType.STRING) //-> DB 테이블에 저장될 때 상수(enum) 이름(문자열)을 사용하기 위해서 쓴 애너테이션
	private Set<MemberRole> roles = new HashSet<>();
	//-> 1명의 멤버가(회원이) 권한을 2개 이상 가질 수 있기 때문에 타입을 Set으로 함.
	//->또, List는 중복값 저장 가능하기 때문에 Set타입 사용(Set은 중복값 저장 X)
	//-> 기본 생성자(모든 필드를 그 필드 타입의 기본값으로 초기화)
	//-> = new HashSet<>(); 기본 생성자보다 우선. null(기본생성자 호출시 기본값)이 아닌 원소가 0인 값으로 세팅
	// null이 되버리면 .쓰고 메서드 호출 불가 하다고 함.
	//nullPointerException 발생 우려.예외 막으려고 넣음
	//문제) 아규먼트가 있는 생성자에서 null값이 될 우려가 있음(빌드 패턴에서 null값우려 -> nullPointerException)
	//해결) @Builder.Default 를 붙임
	
	
	//편의 메서드 만듬 -> 문제 없이 동작 하려면 roles값이 null이 되면 절대 안된다고 함.
	//=> (null이면 nullPointerException발생함. null값이면 .메서드 호출 불가(참조해서 메서드를 호출할 주소가 없기 때문. null이니까))
	public Member addRole(MemberRole role) {
		roles.add(role); // Set<엘리먼트타입>에 원소를 추가
		return this;
	}
	
	public Member clearRoles() {
		roles.clear(); //-> Set<엘리먼트타입> 의 모든 원소를 지움
		return this;
	}
	
}
