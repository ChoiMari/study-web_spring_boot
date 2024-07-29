package com.itwill.springboot5.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
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

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)//-> 외부에 공개하지 않는 private 생성자(모든 필드를 초기화하는 생성자)-> 빌터패턴 위해서 사용함
@NoArgsConstructor
@Getter
@ToString(callSuper = true) //->(callSuper = true) 부모(상위( 클래스의 toString메서드를 호출 해서 하위 클래스의 toString을 만들 때 이용하는 애너테이션의 속성.
//->(callSuper = true) : 상위 클래스의 toString도 포함하게 만드는 속성.
//->ToString의 기본값 :  클래스의 필드만을 이용해서 만듬. 수퍼클래스이용하지 않음. 그래서 속성을 추가 했음
@EqualsAndHashCode(callSuper = true) //->(callSuper = true): 상위(부모) 클래스의 필드들도 사용해서 equals(), hashcode() 만듬
@Entity //-> 엔터티 클래스라고 알려주는 애너테이션
@Table(name = "POSTS")//-> 실제 DB테이블과 이름이 달라서 작성함
public class Post extends BaseTimeEntity{
	
	@Id//PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)//generated as identity-> 자동 번호 부여해서 생성함
	private Long id;
	
	@Basic(optional = false) //-> not null 컬럼임을 알려줌
	private String title; 
	
	//엔터티에서 글자수를 검증하지 않음. 검증할거면 하이버네이트의  벨리데이션 관련 애너테이션 따로 써야한다고 함
	//금요일에 넣었던 글자수는 테이블 생성할거 아니면 쓸 필요 없다고 함
	@Basic(optional = false) //-> not null 컬럼임을 알려줌
	private String content;
	
	@Basic(optional = false) //-> not null 컬럼임을 알려줌
	private String author;
	
	// 생성 수정시간은 수퍼클래스로 만들고 상속 받았음.extends BaseTimeEntity
	// <클래스 BaseTimeEntity에서 선언함>
//	@CreatedDate //-> 엔터티 (최초)생성 시간을 저장하는 필드임을 알려주는 애너테이션
//	private LocalDateTime createdTime;
//	@LastModifiedDate //-> 엔터티 (최종) 수정 시간을 저장하는 애너테이션
//	private LocalDateTime modifiedTime;
	
	//궁금하면 벨레데이션 애너테이션 검색해보라고 하심.
	//엔터티 객체 생성부터 글자수 조건, 정규표현식 검증을 JPA에서 수행해 줄 수 있는 애너테이션이 있다고 함
	
	//title& content는 update 기능이 되어야함. //-> 그래서 메서드 만듬
	//update 기능(제목, 내용 수정 기능) 에서 사용할 공개 메서드
	public Post update(String title, String content) {
		this.title = title;
		this.content = content;
		
		return this;//-> 자기자신 리턴.(필드 초기화해서 리턴함)
	}
	
}
