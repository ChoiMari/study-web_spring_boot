package com.itwill.springboot4.domain;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity 
@Table(name = "ANSWERS") //-> DB 테이블이름과 엔터티 클래스의 이름이 다른 경우에만 사용함
public class Answer {
	
	@Id//pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //-> 오라클 에서의 숫자 자동 증가하는 컬럼
	@Column(name = "AID")//-> 테이블의 컬럼 이름과 엔터티 클래스의 필드 이름이 다른경우에 사용함. 테이블에서의 이름은 AID컬럼
	private Long id;
	
	@ManyToOne//Answers을 기준으로 한 Question테이블과의 관계. 질문 1에 대한 답변 여러개, 질문 2에 대한 답변 여러개 일 수 있다.
	//->다대일 관계. 
	//@JoinColumn 설정을 하지 않은 경우 컬럼 이름은 : question_Question 엔터티의 @Id로 선언한 필드이름으로 실제 테이블의 컬럼이름이 됨.	
	//private Question question; //->qeustion_qid 컬럼이름으로 만들어짐. JPA의 관습으로 지어짐(조인컬럼이름)
	//-> 필드이름_Question의 @Id 이름으로 만듬. @JoinColumn(조인컬럼)이라고 명시하지 않은 경우에..
	@JoinColumn(name = "QUESTION_ID") //->question_id 이렇게 컬럼이름이 만들어짐
	private Question question;
	//@ManyToOne 관계일때는 @ManyToOne으로 선언한게 fk가 됨. pk는 Question의 @Id로 선언한 필드를 찾아감
	//단방향 @OneToMany는 좋지 않다고 함.
	
	private String answer;

}
