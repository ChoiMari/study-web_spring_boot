package com.itwill.springboot5.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot5.domain.Comment;
import com.itwill.springboot5.domain.Post;

//<Comment, Long> : 첫번째 엔터티 타입, 두번째 @Id로 설정한 엔터티의 필드 타입으로 설정
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	
	//JPA 쿼리 메서드 이용(약속된 키워드로 메서드 이름만 만들면 자동으로 구현되는 방식)
	//postId로 검색하기 - 페이징 처리한 댓글 목록 select
//	Page<Comment> findByPostId(Long id, Pageable pageable);
	//-> Post타입의 객체로 검색 - 페이징 처리한 댓글 목록 select
	Page<Comment> findByPost(Post post, Pageable pageable); 
	//-> CommentService에서 사용함
	


}
