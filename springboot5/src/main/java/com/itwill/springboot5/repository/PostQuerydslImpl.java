package com.itwill.springboot5.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.domain.QPost;
import com.itwill.springboot5.dto.PostSearchRequestDto;
import com.querydsl.core.BooleanBuilder;
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

	@Override // 제목 또는(or) 내용에 포함된 문자열 대소문자 구분 없이 검색
	public List<Post> searchByTitleOrContent(String keyword) {
		log.info("searchByTitleOrContent(keyword={})",keyword);
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post)
				.where(post.title.containsIgnoreCase(keyword) 
						.or(post.content.containsIgnoreCase(keyword))).orderBy(post.id.desc());
		//->containsIgnoreCase()메서드의 리턴타입 BooleanExpression 뒤에는 or 또는 and 붙일 수 있음
		
		List<Post> result = query.fetch();
		return result;
	}

	@Override // 수정시간(modifiedTime)이 from부터 to 사이에 포함된 검색(기간을 포함하는. 언제부터 언제까지)
	//(where modified_time between ? and ?) 수정시간 범위로 검색하기(언제부터 언제까지)
	//수정시간을 기준으로 내림차순(desc)함
	public List<Post> searchByModifiedTime(LocalDateTime from, LocalDateTime to) {
		log.info("searchByModifiedTime(from={},to={})",from,to);
		
		QPost post = QPost.post;
		
		JPQLQuery<Post> query = from(post)
				.where(post.modifiedTime.between(from, to))
				.orderBy(post.modifiedTime.desc());
		List<Post> result = query.fetch();
		return result;
	}

	
	//상세 검색 : 작성자와 제목으로 검색하기. (작성자는 일치, 제목은 검색어에 포함하는)
	// where author = ? and lower(title) like ?
	@Override
	public List<Post> searchByAuthorAndTitle(String author, String title) {
		log.info("searchByAuthorAndTitle(author={},title={})",author,title);
		
		//Q클래스 찾음
		QPost post = QPost.post;
		
		//쿼리객체만듬 from메서드안에 아규먼트로 Q클래스객체 넣음
		JPQLQuery<Post> query = from(post) //select * from post 절
				.where(post.author.eq(author)
						.and(post.title.containsIgnoreCase(title)))  //where절
				.orderBy(post.id.desc()); //order by 정렬 id를 기준으로 하는 내림차순
		
	//	List<Post> result = query.fetch();
		
		return query.fetch();//-> 변수에 안 담고 바로 메서드 호출해서 리턴값 보내도 됨.
	}

	//다이나믹 쿼리(동적 쿼리) ----------------------------------------------
	@Override //제목/내용/제목+내용/작성자 검색
	public List<Post> searchByCategory(PostSearchRequestDto dto) {
		log.info("searchByCategory(dtp={})",dto);
		
		String category = dto.getCategory();
		String keyword = dto.getKeyword();
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post);
		
		BooleanBuilder builder = new BooleanBuilder(); //-> 기본생성자 호출로 객체 생성함
		// where()메서드의 아규먼트인 BooleanExpression 객체를 생성할 수 있는 객체.
		//boolean표현식(BooleanExpression)을 만들어 줄 수 있는 객체.
		//BooleanBuilder 사용법
		switch(category) {
		case "t":
			builder.and(post.title.containsIgnoreCase(keyword));
			//builder.and() :  where안에 썼던 방식 쓰면 된다고 
			//비어있는 조건(BooleanBuilder builder = new BooleanBuilder();)에다 and로 연결한다는 뜻이라함
			break;
		case "c":
			builder.and(post.content.containsIgnoreCase(keyword));
			break;
		case "tc":
			builder.and(post.title.containsIgnoreCase(keyword))
				.or(post.content.containsIgnoreCase(keyword));
			break;
		case "a":
			builder.and(post.author.containsIgnoreCase(keyword));
			break;
		}
		//서로 다른 조건들을 builder에 담아서 where의 아규먼트로 넣음
		query.where(builder).orderBy(post.id.desc());
		
		return query.fetch();
	}

//	//제목 또는 내용에 검색어들 중 한 개라도 포함되어 있는 레코드들을 검색(공백과 다른 단어 섞여있어도 상관없는 검색)
//	@Override
//	public List<Post> searchByKeywords(String[] keywords) {
//	    
//	    log.info("searchByKeywords(keywords={})", (Object) keywords); 
//	    //->(Object)로 타입 강제변환함 - 배열을 개별 요소가 아닌 하나의 객체로 취급하여 올바르게 출력되도록 하기 위해 사용됩니다.
//	    //(Object) 캐스트를 하지 않고 단순히 keywords 배열을 log.info()에 전달하면, 
//	    // 열의 내용을 올바르게 출력하지 못하고, 배열의 메모리 주소를 출력하거나 예외가 발생할 수 있습니다.
//	    
//	    // QPost는 QueryDSL에서 생성된 쿼리 타입 클래스입니다. 이 클래스는 Post 엔티티를
//	    // 기준으로 JPQL 쿼리를 작성할 수 있는 필드와 메서드를 제공합니다.
//	    QPost post = QPost.post;
//	    
//	    // JPQLQuery는 QueryDSL에서 JPQL 쿼리를 작성하고 실행하는 객체입니다.
//	    // from(post)를 통해 기본 쿼리 대상 엔티티를 지정합니다.
//	    // JPQLQuery는 QueryDSL을 사용해 데이터베이스에서 데이터를 가져오는 쿼리를 만들고 실행하는 도구입니다.
//	    // from(post)는 쿼리에서 어떤 데이터를 찾을지 정하는 부분으로, 여기서는 Post라는 테이블(또는 엔티티)에서 데이터를 찾겠다는 의미입니다.
//	    JPQLQuery<Post> query = from(post);
//	    
//	    // BooleanBuilder는 동적 쿼리에서 조건을 조합할 때 사용되는 객체입니다.
//	    // 여기서는 검색 키워드에 따라 제목 또는 내용에 해당 키워드가 포함되어 있는지
//	    // 확인하는 조건을 동적으로 추가합니다.
//	    BooleanBuilder builder = new BooleanBuilder();
//
//	    // 키워드 배열을 순회하면서 각각의 키워드에 대해 조건을 추가
//	    // 키워드 배열을 순회하면서 각각의 키워드에 대해 조건을 추가합니다.
//	    // - keyword가 null이 아니고 빈 문자열이 아닌 경우에만 조건을 추가합니다.
//	    // - containsIgnoreCase() 메서드를 사용하여 제목(title) 또는 내용(content)에
//	    //   키워드가 포함되어 있는지를 대소문자 구분 없이 검색합니다.
//	    // - or() 메서드를 사용하여 여러 조건이 논리적으로 OR 연산되도록 조합합니다.
//	    for (String keyword : keywords) {
//	        if (keyword != null && !keyword.trim().isEmpty()) {
//	            // 제목 또는 내용에 키워드가 포함되어 있는지 확인
//	        	 // 각 키워드가 제목 또는 내용에 포함되어 있는지 확인하고, 조건을 OR로 연결
//	            builder.or(post.title.containsIgnoreCase(keyword))
//	                   .or(post.content.containsIgnoreCase(keyword));
//	        }
//	    }
//
//	    // 조건이 추가된 builder를 사용하여 쿼리 실행
//	    // BooleanBuilder에 추가된 조건을 JPQL 쿼리에 적용합니다.
//	    // - where(builder)를 호출하여, 이전에 추가한 모든 조건을 쿼리에 반영합니다.
//	    // - 이로써 쿼리는 "제목 또는 내용에 키워드 중 하나라도 포함된 게시물"을
//	    //   검색하는 형태로 구성됩니다.
//	    query.where(builder);
//
//	    // 쿼리 실행 후 결과 반환
//	    // 쿼리를 실행하고, 결과를 리스트로 반환합니다.
//	    // - fetch() 메서드는 쿼리를 실행하여 결과를 가져옵니다.
//	    // - 이 경우, Post 엔티티 객체의 리스트를 반환합니다.
//	    return query.fetch();
//	}
	
	
	//제목 또는 내용에 검색어들 중 한 개라도 포함되어 있는 레코드들을 검색(공백과 다른 단어 섞여있어도 상관없는 검색)
	@Override
	public List<Post> searchByKeywords(String[] keywords) { 
		//->String[] keywords에 적어도 1개 이상의 검색어가 있다고 가정하고 코드 구현함
	    
	    //log.info("searchByKeywords(keywords={})", (Object) keywords); 
		log.info("searchByKeywords(keywords={})", Arrays.asList(keywords)); 
	    //-> 배열인 경우 로그에서 출력하고 싶으면 
		//Arrays.asList메서드 사용해서 list변환하기 Arrays.asList() : 문자열들의 배열을 list로 만들어 준다고 함

	    QPost post = QPost.post;
	    
	    JPQLQuery<Post> query = from(post);
	    
	    //동적쿼리 만들기 위해서 BooleanBuilder객체 생성
	    BooleanBuilder builder = new BooleanBuilder();

	    
	    for (String k : keywords) { //배열 keywords에서 원소 개수만큼 1개씩 꺼내서 변수k에 담아 반복문 실행(향상된for문 사용)
	        if (k != null && !k.trim().isEmpty()) {
	        	//builder가 반복하면서 또는 이라는 조건이 필요하기 때문에 or로 연결(중요)
	            builder.or(post.title.containsIgnoreCase(k)
	                   .or(post.content.containsIgnoreCase(k)));
	        } // 첫번째 단어가 제목에 들어가 있거나 내용에 들어가 있거나(대소문자 구분없이)
	        //배열 keywords의 원소개수만큼 계속 반복.//괄호 중요하다고 함.
	        //반복되면 안쪽으로 묶임.
	    }

	    query.where(builder).orderBy(post.id.desc());

	    return query.fetch();
	}

	@Override
	public Page<Post> searchByKeywords(String[] keywords, Pageable pageable) {
		log.info("searchByKeywords(keywords={}, pageable={})", 
					Arrays.asList(keywords), pageable); 
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post);
		BooleanBuilder buider = new BooleanBuilder();
		
		for(String k : keywords) {
			buider.or(post.title.containsIgnoreCase(k)
					.or(post.content.containsIgnoreCase(k)));
		}
		query.where(buider);
		
		//Paging & Sorting 적용(정렬은 Pageable객체 만들때 설정함 Sort.by("정렬할 기준이 될 컬럼이름") 써서..)
		getQuerydsl().applyPagination(pageable, query);
		//applyPagination() 메서드 : 2번째 아규먼트로 넣은 쿼리를 페이징처리해서 쿼리로 바꿔준다고 함
		
		//한 페이지에 표시할 데이터를 select(fetch함)
		List<Post> list = query.fetch();//query.fetch() 는 List<Post>를 리턴함(Page 타입으로 리턴해 주지않음) 그래서 일단 변수에 담고 처리.
		log.info("list.size={}",list.size()); //pageable에 한 페이지당 보여줄 개수를 5를 넣었으면 list.size가 5라고 함
		
		//전체 레코드 개수를 fetch.(select) 해서 가져옴 - 페이징 처리에 필요해서
		long count = query.fetchCount(); //-> 페이징 처리전의 전체 레코드 개수 리턴받음
		log.info("fetch count ={}",count);
		
		//Page<T> 객체를 생성 (T는 엔터티 클래스) Page는 인터페이스 Page를 구현한 클래스 PageImpl을 호출함
		Page<Post> Page = new PageImpl<>(list, pageable, count);
		//첫번째 아규먼트 - 현재 페이지에 포함된 데이터 목록(해당 페이지에 보여줄 데이터)
		//두번째 아규먼트 - 페이지 정보와 정렬 방식을 포함한 객체
		//예를 들어, 어떤 페이지(예: 첫 번째 페이지)를 요청했는지, 한 페이지에 몇 개의 데이터가 필요한지, 데이터를 어떤 순서로 정렬할지를 포함합니다.
		//세번째 아규먼트 - 전체 데이터 개수
		//예를 들어, 데이터베이스에 있는 전체 게시물의 개수입니다. 이 값은 총 몇 페이지가 필요한지를 계산하는 데 사용됩니다.
		
		return Page;
	}
	
	
	
	
	
}
