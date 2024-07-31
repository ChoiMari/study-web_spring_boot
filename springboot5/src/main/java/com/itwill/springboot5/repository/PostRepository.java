package com.itwill.springboot5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.springboot5.domain.Post;

//엔터티 클래스 타입, @Id 필드 타입 <,> 안에 각각 적어주기
//extends JpaRepository : CRUD, Paging/Sorting 기본적인 sql과 페이징 처리, 정렬 처리 기능 
public interface PostRepository extends JpaRepository<Post, Long>, PostQuerydsl{

	//JPA Query Method 작성 : (약속된 키워드로 메서드 이름지으면 만들어짐)
	// 제목에 포함된(Containing) 문자열 대소문자 구분없이(IgnoreCase) 검색하기
	Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	
	//내용에 포함된 문자열 대소문자 구분없이 검색하기:
	Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
	
	//작성자에 포함된 문자열을 대소문자 구분없이 검색하기:
	Page<Post> findByAuthorContainingIgnoreCase(String keyword, Pageable pageable);
	
	//JPQL(Java Persistence Query Language) : 객체지향 쿼리 언어
	//제목 또는(or) 내용에 포함된 문자열 대소문자 구분없이 검색하기 :
	//Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(아규먼트 선언);
	// 좀 더 간단하게 Page<Post> findByTitleContainingOrContentContainingAllIgnoreCase(args);
	//-> 메서드 이름 길어서 -> 쿼리를 개발자가 직접 작성
	@Query("select p from Post p "
			+ "where upper(p.title) like upper('%' || :keyword || '%')"
			+ "or upper(p.content) like upper('%' || :keyword || '%')") //앞뒤로 %% 붙여서 검색어를 대문자로 변환해서 같은지 비교
	//-> 테이블과 컬럼 이름 엔터티 이름과 필드로 작성. 반드시 별명 주어야함
	//-> sql문에 페이징 처리나 정렬 안써도 된다고.. 
	//(옵션 설정하면 실제 테이블이름과 실제 컬럼 이름으로도 sql문장 작성할 수도 있다고 함)
	Page<Post> findByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);
	
	
	
}
