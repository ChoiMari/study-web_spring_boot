package com.itwill.springboot5.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot5.domain.Comment;
import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.CommentRegisterDto;
import com.itwill.springboot5.repository.CommentRepository;
import com.itwill.springboot5.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor //->final필드 초기화 할 수 있는 생성자 만듬 -> 생성자에 의한 의존성 주입 위해 필요함
@Service
public class CommentService {
	
	private final CommentRepository commentRepo;
	private final PostRepository postRepo;
	
	public Comment create(CommentRegisterDto dto) {
		
		log.info("create(dto={})",dto);
		// 댓글이 달릴 포스트 엔터티를 검색:
		Post post = postRepo.findById(dto.getPostId()).orElseThrow();//Optional 타입이라서 .orElseThrow() 호출함
		log.info("post={}",post);
		//DB 테이블에 저장할 Comment 타입의 엔터티를 생성:
		Comment entity = Comment.builder()
				.post(post)  
				.ctext(dto.getCtext())
				.writer(dto.getWriter())
				.build();
		
		log.info("entity save 전={}",entity);
		
		commentRepo.save(entity); //-> save()메서드 아규먼트로 엔터티 타입(여기서는 Comment객체)을 넣어주어야 함
		//->DB에 저장(save(엔터티클래스타입) 메서드 호출로 insert 쿼리 실행)
		
		return entity;
	}
	
	//댓글 목록 가져오는 서비스(findById(postId)로 post객체를 (검색)찾고 post 객체를 아규먼트로 넣어주어 댓글 페이지 객체를 만들어옴)
	@Transactional(readOnly = true) //-> 값을 읽어오기만(select만) 하는 서비스 메서드여서 읽기 전용으로 만듬
	public Page<Comment> readCommentList(Long postId, int pageNo){
		
		log.info("readCommentList(postId={},pageNo={})",postId, pageNo);
		
		//댓글들이 달려있는 포스트 엔터티를 먼저 검색하기
		Post post = postRepo.findById(postId).orElseThrow();
		
		//페이징 처리와 정렬을 하기 위한 Pageable 객체 생성
		Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("modifiedTime").descending()); //수정시간을 기준으로 내림차순정렬(수정시간 최신순)
		
		//DB에서 검색(select 쿼리 실행) ->해당 포스트에 해당하는 댓글 목록 가져옴
		Page<Comment> data = commentRepo.findByPost(post, pageable);
		log.info("data.number 현재 페이지 번호= {}, data.totalPages 전체 페이지 수 = {}",data.getNumber(),data.getTotalPages());
		
		return data;
	}
	
	public void delete(Long id) {
		log.info("delete(id={})",id);
		
		commentRepo.deleteById(id);
	}

}
