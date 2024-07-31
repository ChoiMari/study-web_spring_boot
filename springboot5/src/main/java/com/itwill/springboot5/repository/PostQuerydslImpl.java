package com.itwill.springboot5.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.domain.QPost;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostQuerydslImpl extends QuerydslRepositorySupport implements PostQuerydsl{
	//QuerydslRepositorySupport에는 기본 생성자 없음. 아규먼트만 있는 생성자만 있다고 함
	//class PostQuerydslImpl 하위 클래스에서 부모의 아규먼트를 갖는 생성자 호출
	// 엔터티 클래스를 아규먼트로 줌
	//QuerydslRepositorySupport 상속 받고 PostQuerydsl 인터페이스 구현하겠다 선언함
	public PostQuerydslImpl() {
		super(Post.class);
	}

	@Override
	public Post searchById(Long id) {
		log.info("searchById(id={})",id); //-> id필드 where절
		
		QPost post = QPost.post; 
		JPQLQuery<Post> query = from(post); //-> select p from Post p 까지를 만들어 준걸로 이해하면 된다고
		query.where(post.id.eq(id)); //->eq: 왼쪽값과 아규먼트로 넣은값이 서로 같은지 비교.
		// ne도 있다고 다르다 라고 조건을 만들때 사용
		//gt,lt 도 있다고.. 작다 크다 비교할때.
		//Q클래스를 엔터티클래스 처럼 이용하면 된다고??
		//-> 기존의 만들어진 쿼리에다가 where절을 붙여준거라고
		//-> query + where id = ?
		Post entity = query.fetchOne(); //->  1개만 가져올때는 fetchOne()메서드 호출
		
		return entity; //-> 쿼리dsl 라이브러리를 이용해서 findById();메서드와 같은걸 만듬.
		//-> 동적sql이라고 함 
		// sql관련 메서드들을 호출 함으로 써 sql문장을 만들어나가는 과정이 쿼리dsl이 하는거라고.
		//-> 단점 : 사용하기 쉽지 않음
		//-> 장점 : 동적으로 다양한 쿼리 사용 가능함
		//-> 마이바티스와 다른점. mybatis는 DB에 종속적 (해당하는 DB에 맞게 작성해야)
		//-> 쿼리dsl은 자바 메서드로 이루어져있기 때문에 DB에 의존적이지 않음
	}
	
	
	
}
