package com.itwill.springboot5.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.PostSearchRequestDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostQuerydslTest {
	
	@Autowired private PostRepository postRepo; //PostQuerydsl인터페이스를 상속 받고 있기 때문에 PostRepository 만 선언하면 된다고 함
	
	//@Test
	public void testSearchById() {
		Post entity = postRepo.searchById(2L);
		log.info("entity = {}", entity);
	}
	
	@Test
	public void test() {
		List<Post> result = null;
		//result = postRepo.searchByTitle("DUMM");		
//		result = postRepo.searchByContent("tes");
//		result = postRepo.searchByTitleOrContent("Du");
		
//		LocalDateTime from = LocalDateTime.of(2024, 7, 30,15,00);
//		LocalDateTime to = LocalDateTime.of(2024, 7, 31,15,30);
//		result = postRepo.searchByModifiedTime(from,to);
//		result = postRepo.searchByModifiedTime(LocalDateTime.of(2024, 7, 30,15,00),LocalDateTime.of(2024, 7, 31,15,30));
		
//		result = postRepo.searchByAuthorAndTitle("admin", "테");
		
//		PostSearchRequestDto dto = new PostSearchRequestDto();
//		dto.setCategory("tc"); //-> 제목 + 내용
//		dto.setKeyword("dum title"); //-> 검색어 ES(포함된 단어 대소문자 구분없이 검색)
//		result = postRepo.searchByCategory(dto);
		
		String[] keywords = "dum title".split(" "); //.split(" ") : "dum title"여기 부분을 공백으로 잘라라.String[] 으로 리턴해 줌.
		//-> {"dum", "title"}; 배열에 원소가 1번째 dum, 2번째 title가 됨(공백으로 잘라서)//-> 검색어에 들어갈 배열 만듬.
//		result = postRepo.searchByKeywords(keywords);
		Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
		//->pageNumber은 페이지번호. 0이 1페이지라는 뜻(0페이지), 페이지당 보여줄 개수 5개씩, id컬럼을 기준으로 내림차순 정렬로 페이징처리
		Page<Post> page = postRepo.searchByKeywords(keywords, pageable);
		
		page.forEach(System.out::println);
	//	result.forEach(System.out::println);
//		for(int i=0; i < 5; i++) {//-> DUMM로 검색했는데 너무 많이 나오니까(더미데이터 많음) 5개만 보려고..
//			log.info("{}",result.get(i));//result.get(i) result에서 인덱스로(get(i)) 값 꺼내서 로그로 출력함
//		}
		
	}
	

}
