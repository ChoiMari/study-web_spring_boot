package com.itwill.springboot5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot5.domain.Comment;

//<Comment, Long> : 첫번째 엔터티 타입, 두번째 @Id로 설정한 엔터티의 필드 타입으로 설정
public interface CommentRepository extends JpaRepository<Comment, Long> {
	

	


}
