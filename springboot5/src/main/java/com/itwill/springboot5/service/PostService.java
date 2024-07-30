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
import com.itwill.springboot5.dto.PostDetailsDto;
import com.itwill.springboot5.dto.PostListItemDto;
import com.itwill.springboot5.dto.PostUpdateDto;
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
	
	//글 상세보기 (id로 select해옴)
	@Transactional(readOnly = true) //-> 읽기 전용으로 만듬.(Defaults to false. 디폴트는 false)
	public Post readById(Long id) {
		log.info("readById(id={})",id);
		Post entity = postRepo.findById(id).orElseThrow();
		log.info("entity = {}",entity);
		return entity;
}
//	public PostDetailsDto readById(Long id) {
//		log.info("readById(id={})",id);
//		Post post = postRepo.findById(id).orElseThrow();
//		
//		return PostDetailsDto.fromEntity(post);
//	}
	//상세보기 페이지에서 삭제 시키는 서비스(id로 삭제시킴)
	@Transactional
	public void delete(Long id) {
		log.info("delete(id={})",id);
		postRepo.deleteById(id); //-> 예외만 발생하지 않으면 delete 성공했다고 생각하면 된다고 함.
	}
	
	//상세보기 페이지에서 업데이트(수정)시키는 서비스
	@Transactional //-> (readOnly = true)으로 설정하지 않으면 기본값false
	public void update(PostUpdateDto dto) {
		log.info("update(dto={})",dto);
		
		//Post entity = dto.toEntity();
		//1. id로 Post 엔터티 객체를 찾음(DB에서 id로 select쿼리를 실행하자 -> findById(id);)
		Post entity = postRepo.findById(dto.getId()).orElseThrow();
		log.info("findById 결과 = {}", entity);
		
		//2. DB에서 검색한 엔터티 객체의 필드들을 업데이트(수정) Post(엔터티 클래스에) update하는 메서드 만들었음. 
		//변경한 부분의 필드값만 초기화시켜서 변경시키는 메서드.
		//DB에 직접적으로 변경은 안함
		entity.update(dto.getTitle(),dto.getContent()); //-> 우리가 수업시간에 만든 메서드
		//-> 리턴값 저장하지 않아두 됨
		log.info("update 호출 후= {}",entity);//-> 여기까지는 제목, 내용만 바뀜.(수정 시간은 안 바뀜)
		
		//3.@Transactional 쓰기 가능한 상태((readOnly = true)으로 설정하지 않음 기본값 false여서 쓰기 가능상태)
		//@Transactional 애너 테이션을 사용한 경우
		//-> DB에서 검색한 entity 객체가 변경되면 update 쿼리가 자동으로 실행된다.(setter메서드 대신에 update 메서드 만들었다고 함)
		// 일단 먼저 검색을 해야함.
		
		//@Transactional을 사용하지 않은 경우 entity = postRepo.save(entity); 메서드를 직접 호출해서 실행해야한다.
		//entity = postRepo.save(entity);//-> 수정된 entity를 아규먼트로 넘김. 그럼 update쿼리 실행 : @Id필드가 null이 아닌 경우(레코드가 있는 경우)
		//똑같은 메서드인데 @Id 필드가 null이면 insert실행 있으면 UPDATE실행.
		//아이디 1번으로 select를 다시함
		//리턴값 저장하지 않아도 된다고 했는데 로그 출력이 잘 안되서 entity = 로 리턴값 저장함.
		//다른부분이 있으면 update됨. modifiedTime 자동으로 채워서 바꿈
		//-> @Id 필드가 null이 아닌 경우(레코드가 있는 경우)와
		// 엔터티 객체가 DB에 있는 레코드와 다른경우(저장된 데이터가 달라진 경우) update쿼리가 실행 된다.
		
		
		//-> 유니테스트 동안에만 동작방식 설명 들은거고 실제는 @트렌젝션 어쩌고로 변경한다고함
		//-> find먼저하고 업데이트 한다고? 이런식으로 한다고 함.
		//log.info("save 호출 후= {}",entity);
		
		
	}
	
	

}
