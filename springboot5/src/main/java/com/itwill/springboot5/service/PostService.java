package com.itwill.springboot5.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.PostCreateDto;
import com.itwill.springboot5.dto.PostListItemDto;
import com.itwill.springboot5.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j//-> 로그 출력 위해
@RequiredArgsConstructor//->final필드 생성자에 의한 의존성 주입 사용하기 위해
@Service
public class PostService {
	
	private final PostRepository postRepo;
	
	//그냥 목록 불러오기
	public List<PostListItemDto> read(){
		log.info("read()");
		//TODO : 영속성(persistence/repository) 계층의 메서드를 호출해서 엔터티들의 list를 가져온다.
		List<Post> list = postRepo.findAll();
		log.info("list size = {}", list.size());
		
		//List<Post>를 List<PostListItemDto>타입으로 변환하기 -> 리턴
	
		return list.stream().map(PostListItemDto::fromEntity).toList();
		// (x) -> PostListItemDto.fromEntity(x).toList(); 원소 1개를 stream으로 한개씩 꺼내서 dto타입으로 변환해서 다시 list로 묶음.
	}
	
	//페이징 처리
	public Page<PostListItemDto> pageRead(int pageNo, Sort sort){
		log.info("read()");
		
		Pageable pageable = PageRequest.of(pageNo, 10, sort); 
		//TODO : 영속성(persistence/repository) 계층의 메서드를 호출해서 엔터티들의 list를 가져온다.
		Page<Post> page = postRepo.findAll(pageable);
		
		//List<Post>를 List<PostListItemDto>타입으로 변환하기 -> 리턴
	
		return page.map(PostListItemDto::fromEntity);
	}
	
	public void insert(PostCreateDto dto) {
		log.info("insert(dto={})",dto);
		Post post = dto.toEntity();
		//post = postRepo.save(post);
		postRepo.save(post);
	}
	

}
