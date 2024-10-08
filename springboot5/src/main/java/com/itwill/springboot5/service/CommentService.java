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
import com.itwill.springboot5.dto.CommentUpdateDto;
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
	
	@Transactional //-> 이걸 써야지 update메서드 호출하면 update됨
	//->@Transactional 쓴 경우 엔터티를 findById 등의 메서드로 검색한 후, 엔터티가 변경되면 자동으로 update 쿼리가 실행 됨.
	//->@Transactional 쓴 경우 JpaRepository<T,ID>의 save(entity) 메서드를 명시적으로 호출할 필요가 없음.
	public void update(CommentUpdateDto dto) {
		log.info("update(dto={})",dto);
		
		//댓글 아이디(PK)로 엔터티를 검색.
		Comment entity = commentRepo.findById(dto.getId()).orElseThrow();
		log.info("findById 결과 = {}", entity);
		
		//검색된 엔터티의 필드를 업데이트
		entity.update(dto.getCtext());
		log.info("update 호출 후= {}",entity);
		
		//@Transactional 쓴 경우 엔터티를 findById 등의 메서드로 검색한 후, 엔터티가 변경되면 자동으로 update 쿼리가 실행 됨.
		//commentRepo.save(entity)를 명시적으로 호출하지 않아도 update쿼리가 실행됨.
		//수정전의 엔터티를 찾고 값변경 시켜주면 자동으로 update쿼리 실행됨.(단, @Transactional이 붙은 경우에)
	}

}
