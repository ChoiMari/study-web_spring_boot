package com.itwill.springboot5.repository;

import java.util.List;

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
	//QuerydslRepositorySupport 상속 받고 PostQuerydsl 인터페이스 구현하겠다 선언함(인터페이스는 구현에 제한이 없음)
	//상속은 클래스는 중복상속 불가(2개이상 불가함. 1개밖에 못함)
	public PostQuerydslImpl() {
		super(Post.class);
	}

	@Override //-> PostQuerydsl인터 페이스에서 선언한 메서드의 body 구현
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

	@Override // 제목(title)에 포함된 문자열 대소문자 구별없이 검색
	public List<Post> searchByTitle(String keyword) {
		log.info("searchByTitle(keyword={})",keyword);
		QPost post = QPost.post; //Q클래스 타입의 변수 선언
		
		// 쿼리 작성하면 됨 extends QuerydslRepositorySupport 상속받아서 여기서 물려주는 메서드 사용가능
		//-> 물려받은 메서드 from()
		JPQLQuery<Post> query = from(post); //->여기까지는 select * from Post 라고 생각하면 됨
		//-> from(); 아규먼트로 Q클래스 타입을 써야함(위에서 선언한 QPost타입의 post를 아규먼트로 넣어줌)
		//->  JPQLQuery<T> 타입을 리턴해줌 T는 엔터티클래스.
		
		//조건(where)절 만듬(where 메서드 호출)
		query.where(post.title.containsIgnoreCase(keyword)); 
		//where조건 제목(title)에 포함된 문자열 대소문자 구별없이(containsIgnoreCase) 검색
		
		//정렬(order by 절)
		query.orderBy(post.id.desc());
		
		//-> 한줄 한줄 각각 나눠서 썼지만 이렇게도 쓸 수 있다고 함.(연쇄적 호출)
	//	JPQLQuery<Post> query = from(post).where(post.title.containsIgnoreCase(keyword)).orderBy(post.id.desc());
		
		//쿼리를 완성했으면 값을 가져오는건 fetch 사용, fetchJoin(), fetchAll()은 리턴 타입 JPQLQuery이라고. 다시 뭔갈 할수 있다고? 함
		List<Post> result = query.fetch();
		
		return result;
		//-> 안드로이드 코드 작성방식이 쿼리 dsl과 비슷하다고함.
	}

	@Override //content(내용)에 포함된 문자열 대소문자 구분없이 검색
	public List<Post> searchByContent(String keyword) {
		log.info("searchByContent(keyword={})",keyword);
		
		QPost post = QPost.post;
		
		JPQLQuery<Post> query = from(post).where(post.content.containsIgnoreCase(keyword)).orderBy(post.id.desc());
		List<Post> result = query.fetch();
		
		return result;
	}
	
	
	
}
