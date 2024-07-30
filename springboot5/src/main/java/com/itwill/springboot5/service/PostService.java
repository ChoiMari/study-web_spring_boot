package com.itwill.springboot5.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
//	//그냥 목록 불러오기
//	@Transactional(readOnly = true) //-> 목록보기에서는 리파지토리에서 검색한 데이터 변경할 이유가 없어서 읽기 전용으로 바꿈.
//	//-> insert/update/delete가 아닌 경우에는 이거 붙여주기
//	public List<PostListItemDto> read(){
//		log.info("read()");
//		// 영속성(persistence/repository) 계층의 메서드를 호출해서 엔터티들의 list를 가져온다.
//		List<Post> list = postRepo.findAll();
//		log.info("list size = {}", list.size());
//		
//		//List<Post>를 List<PostListItemDto>타입으로 변환하기 -> 리턴
//	
//		return list.stream().map(PostListItemDto::fromEntity).toList();
//		// (x) -> PostListItemDto.fromEntity(x).toList(); 원소 1개를 stream으로 한개씩 꺼내서 dto타입으로 변환해서 다시 list로 묶음.
//	}
	
	//페이징 처리한 post 목록 가져오는 서비스
	@Transactional(readOnly = true)
	public Page<PostListItemDto> read(int pageNo, Sort sort){
		log.info("read(pageNo={}, sort={})",pageNo, sort);
		
		// Pageable 객체 생성 -> findAll메서드의 아규먼트로 넘겨줌
		Pageable pageable = PageRequest.of(pageNo, 5, sort); 
		// 영속성(persistence/repository) 계층의 메서드를 호출해서 엔터티들의 list를 가져온다.
		Page<Post> page = postRepo.findAll(pageable); 
		
		log.info("page.getTotalPages={}",page.getTotalPages());//-> 전체 페이지 개수. 뷰에서 page.totalPages에서 쓴 것과 같음
		log.info("page.number={}",page.getNumber()); //-> page.getNumber() 현재 페이지 번호. pageNo와 같아야 함.
		//뷰에서 page.number 쓴것과 같음
		
		log.info("page.hasPrevious={}",page.hasPrevious()); // 이전 페이지가 있는지 여부. 뷰에서 page.hasPrevious 쓴 것과 같음
		log.info("page.hasNext={}",page.hasNext());// 다음페이지가 있는지 여부. 뷰에서 page.hasNext 쓴 것과 같음
		
		//Page<Post> 객체를 Page<PostListItemDto> 타입으로 변환해서 리턴.
		return page.map(PostListItemDto::fromEntity); //-> 변수에 담아서 리턴해도 되고 바로 메서드 호출해서 리턴값으로 넘겨도 됨.
		//-> (x) -> PostListItemDto.fromEntity(x) 와 같음
	}
	
	@Transactional
	public Long create(PostCreateDto dto) {
		log.info("cteate(dto={})",dto);
//		Post post = dto.toEntity();
//		//post = postRepo.save(post);
//		postRepo.save(post);
		
		//영속성 계층의 메서드를 호출해서 실제 DB에 insert 쿼리를 실행
		Post entity = postRepo.save(dto.toEntity()); //dto.toEntity() 리턴값이 post타입으로 변환해서 리턴해주기 때문에 아규먼트로 넣음
		
		log.info("entity = {}",entity);
		
		log.info("insert id = {}",entity.getId());
		
		return entity.getId(); //-> 자동으로 생성되는 PK를 리턴하겠다(insert시킨 PK를 리턴함)
		//-> DB에 insert된 레코드의 PK(id)를 리턴.
		//-> entity 자체를 리턴해도 됨. 설계하기 나름.
	}
	

}
