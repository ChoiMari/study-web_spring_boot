package com.itwill.springboot5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot5.domain.Post;

//엔터티 클래스 타입, @Id 필드 타입 <,> 안에 각각 적어주기
//extends JpaRepository : CRUD, Paging/Sorting 기본적인 sql과 페이징 처리, 정렬 처리 기능 
public interface PostRepository extends JpaRepository<Post, Long>{

}
