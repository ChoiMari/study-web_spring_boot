package com.itwill.springboot5.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 외부공개 X : private로 설정
@Builder 
@Getter @ToString(callSuper = true) //->(callSuper = true) :toString을 만들 때 부모클래스의 필드도 이용하는 애너테이션의 속성. 
@EqualsAndHashCode(callSuper = true) //-> 객체끼리 비교 위해서. 수퍼클래스도 같이 호출하겠다라고 설정함
@Entity @Table(name = "COMMENTS")
public class Comment extends BaseTimeEntity { //extends BaseTimeEntity : 생성시간, 수정시간 여기에
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //->generated as identity로 값이 생성된다고 설정
	private Long id;
	
	
	//private Long postId;
	@ToString.Exclude //-> toString 메서드를 만들 때 해당 필드는 제외시키는 애너테이션 //-> 조인할거면 이거해야한다고 함
	@ManyToOne(fetch = FetchType.LAZY) //1번게시글에 해당하는 댓글 많음(다대일 관계 - comments를 기준으로)
	@JoinColumn(name ="POST_ID")//-> 조인시 꼭 필요. name값에 실제 테이블의 실제 컬럼 이름을 작성함 //-> FK제약조건이 있는 컬럼 이름
	private Post post;//->postId 필드필요한데 엔터티 타입으로 하고 변수이름도 변경함
	
	@Basic(optional = false)
	private String ctext;
	
	@Basic(optional = false)
	private String writer;
	
	
	public Comment update(String ctext) {
		this.ctext = ctext;
		return this;
	}	
}
